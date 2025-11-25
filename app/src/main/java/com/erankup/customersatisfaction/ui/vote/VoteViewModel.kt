package com.erankup.customersatisfaction.ui.vote

import android.content.Context
import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erankup.customersatisfaction.data.repository.VoteRepository
import com.erankup.customersatisfaction.di.IoDispatcher
import com.erankup.customersatisfaction.domain.model.FeedbackData
import com.erankup.customersatisfaction.domain.model.Question
import com.erankup.customersatisfaction.domain.model.QuestionAnswer
import com.erankup.customersatisfaction.domain.model.VoteType
import com.erankup.customersatisfaction.util.DeviceConfigManager
import com.erankup.customersatisfaction.util.DeviceThemeConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

@HiltViewModel
class VoteViewModel @Inject constructor(
    private val repository: VoteRepository,
    private val deviceConfigManager: DeviceConfigManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        VoteUiState(
            baseUrl = deviceConfigManager.getDefaultBaseUrl(),
            themeColors = VoteThemeColors.Default,
            headerBackgroundColor = DeviceConfigManager.DEFAULT_HEADER_BACKGROUND_COLOR,
            headerTextColor = DeviceConfigManager.DEFAULT_HEADER_TEXT_COLOR,
            bodyBackgroundColor = DeviceConfigManager.DEFAULT_BODY_BACKGROUND_COLOR,
            bodyTextColor = DeviceConfigManager.DEFAULT_BODY_TEXT_COLOR,
            accentColor = DeviceConfigManager.DEFAULT_ACCENT_COLOR
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<VoteUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()
    private var inactivityJob: Job? = null

    init {
        refreshDeviceConfig()
    }

    private fun scheduleInactivityTimeout() {
        inactivityJob?.cancel()
        inactivityJob = viewModelScope.launch {
            kotlinx.coroutines.delay(60_000L)
            resetSessionDueToInactivity()
        }
    }

    private fun resetSessionDueToInactivity() {
        val state = _uiState.value
        if (!state.hasDeviceConfig || state.questionsLoading || state.questions.isEmpty()) {
            return
        }
        _uiState.update {
            it.copy(
                answers = emptyList(),
                currentQuestionIndex = 0,
                isFeedbackStage = false,
                feedbackName = "",
                feedbackPhone = "",
                feedbackEmail = "",
                feedbackComment = "",
                marketingConsent = false,
                isLoading = false,
                questionsError = null
            )
        }
        state.deviceOwner.takeIf { owner -> owner.isNotBlank() }?.let { owner ->
            val token = state.deviceToken
            if (token.isNotBlank()) {
                loadQuestions(owner = owner, token = token)
            }
        }
    }

    private fun refreshDeviceConfig() {
        viewModelScope.launch {
            val config = withContext(ioDispatcher) { deviceConfigManager.getDeviceConfig() }
            _uiState.update { state ->
                state.copy(
                    deviceToken = config.token.orEmpty(),
                    deviceOwner = config.owner.orEmpty(),
                    baseUrl = config.baseUrl,
                    headerTitle = config.headerTitle ?: config.owner.orEmpty(),
                    headerLogoPath = config.headerLogoPath,
                    hasDeviceConfig = !config.token.isNullOrBlank(),
                    headerBackgroundColor = config.theme.headerBackgroundColor,
                    headerTextColor = config.theme.headerTextColor,
                    bodyBackgroundColor = config.theme.bodyBackgroundColor,
                    bodyTextColor = config.theme.bodyTextColor,
                    accentColor = config.theme.accentColor,
                    themeColors = config.theme.toVoteThemeColors()
                )
            }
            val token = config.token
            val owner = config.owner
            if (!token.isNullOrBlank() && !owner.isNullOrBlank()) {
                loadQuestions(owner = owner, token = token)
            } else {
                _uiState.update { state ->
                    state.copy(
                        questions = emptyList(),
                        answers = emptyList(),
                        currentQuestionIndex = 0,
                        isFeedbackStage = false,
                        questionsLoading = false,
                        questionsError = null
                    )
                }
            }
        }
    }

    private fun loadQuestions(owner: String, token: String) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    questionsLoading = true,
                    questionsError = null,
                    answers = emptyList(),
                    currentQuestionIndex = 0,
                    isFeedbackStage = false
                )
            }
            val result = withContext(ioDispatcher) { repository.fetchQuestions(owner) }
            result
                .onSuccess { questions ->
                    _uiState.update { state ->
                        state.copy(
                            questions = questions,
                            questionsLoading = false,
                            questionsError = null,
                            currentQuestionIndex = 0,
                            answers = emptyList(),
                            isFeedbackStage = questions.isEmpty()
                        )
                    }
                }
                .onFailure { throwable ->
                    val message = throwable.message?.ifBlank { null }
                        ?: "Неуспех при зареждане на въпросите."
                    _uiState.update { state ->
                        state.copy(
                            questionsLoading = false,
                            questionsError = message
                        )
                    }
                    _uiEvent.emit(
                        VoteUiEvent.Error(message = message)
                    )
                }
        }
    }

    fun onVoteSelected(voteType: VoteType) {
        val currentState = _uiState.value
        if (!currentState.hasDeviceConfig || currentState.questionsLoading || currentState.isFeedbackStage) {
            return
        }
        scheduleInactivityTimeout()
        val questions = currentState.questions
        if (questions.isEmpty()) {
            _uiState.update { it.copy(isFeedbackStage = true) }
            return
        }
        val currentIndex = currentState.currentQuestionIndex.coerceIn(0, questions.lastIndex)
        val currentQuestion = questions[currentIndex]
        val updatedAnswers = currentState.answers
            .filterNot { it.questionId == currentQuestion.id } + QuestionAnswer(
            questionId = currentQuestion.id,
            questionText = currentQuestion.text,
            voteType = voteType
        )
        val nextIndex = currentIndex + 1
        val reachedFeedbackStage = nextIndex >= questions.size
        _uiState.update { state ->
            state.copy(
                answers = updatedAnswers,
                currentQuestionIndex = nextIndex.coerceAtMost(questions.lastIndex),
                isFeedbackStage = reachedFeedbackStage
            )
        }
    }

    fun submitSession() {
        viewModelScope.launch {
            val snapshot = _uiState.value
            if (!snapshot.marketingConsent) {
                _uiEvent.emit(
                    VoteUiEvent.Error(
                        message = MESSAGE_MARKETING_CONSENT_REQUIRED
                    )
                )
                return@launch
            }
            if (snapshot.questions.isNotEmpty() && snapshot.answers.size < snapshot.questions.size) {
                _uiEvent.emit(
                    VoteUiEvent.Error(
                        message = MESSAGE_ALL_QUESTIONS_REQUIRED
                    )
                )
                return@launch
            }
            _uiState.update { it.copy(isLoading = true) }
            try {
                val config = withContext(ioDispatcher) { deviceConfigManager.getDeviceConfig() }
                val token = config.token
                val owner = config.owner
                if (token.isNullOrBlank() || owner.isNullOrBlank()) {
                    _uiEvent.emit(
                        VoteUiEvent.Error(
                            message = MESSAGE_TOKEN_REQUIRED
                        )
                    )
                    return@launch
                }
                val currentState = _uiState.value
                val feedbackData = FeedbackData(
                    name = currentState.feedbackName.trim().takeIf { it.isNotEmpty() },
                    phone = currentState.feedbackPhone.trim().takeIf { it.isNotEmpty() },
                    email = currentState.feedbackEmail.trim().takeIf { it.isNotEmpty() },
                    comment = currentState.feedbackComment.trim().takeIf { it.isNotEmpty() },
                    marketingConsent = currentState.marketingConsent
                )
                val answersSnapshot = currentState.answers
                val result = withContext(ioDispatcher) {
                    repository.submitSession(
                        token = token,
                        owner = owner,
                        answers = answersSnapshot,
                        feedbackData = feedbackData
                    )
                }
                result
                    .onSuccess { response ->
                        _uiEvent.emit(
                            VoteUiEvent.Success(
                                message = response.message.ifBlank { "Благодарим за обратната връзка." }
                            )
                        )
                        _uiState.update { state ->
                            state.copy(
                                feedbackName = "",
                                feedbackPhone = "",
                                feedbackEmail = "",
                                feedbackComment = "",
                                marketingConsent = false,
                                answers = emptyList(),
                                currentQuestionIndex = 0,
                                isFeedbackStage = state.questions.isEmpty()
                            )
                        }
                        loadQuestions(owner = owner, token = token)
                    }
                    .onFailure { throwable ->
                        val message = throwable.message?.ifBlank { null }
                        _uiEvent.emit(
                            VoteUiEvent.Error(
                                message = message ?: "Неуспех при изпращане на обратната връзка. Опитайте отново."
                            )
                        )
                    }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun saveDeviceConfig(
        token: String,
        owner: String,
        baseUrl: String,
        headerTitle: String?,
        headerLogoPath: String?,
        headerBackgroundColor: String,
        headerTextColor: String,
        bodyBackgroundColor: String,
        bodyTextColor: String,
        accentColor: String
    ) {
        viewModelScope.launch {
            val normalizedToken = token.trim()
            if (normalizedToken.isBlank()) {
                _uiEvent.emit(
                    VoteUiEvent.Error(
                        message = MESSAGE_TOKEN_REQUIRED
                    )
                )
                return@launch
            }
            val normalizedBaseUrl = deviceConfigManager.normalizeBaseUrl(baseUrl)
            if (normalizedBaseUrl == null) {
                _uiEvent.emit(
                    VoteUiEvent.Error(
                        message = MESSAGE_BASE_URL_INVALID
                    )
                )
                return@launch
            }
            val normalizedOwner = owner.trim().ifBlank { null }
            val normalizedHeaderTitle = headerTitle?.trim().takeUnless { it.isNullOrEmpty() }

            withContext(ioDispatcher) {
                deviceConfigManager.saveDeviceConfig(
                    token = normalizedToken,
                    owner = normalizedOwner,
                    baseUrl = normalizedBaseUrl,
                    headerTitle = normalizedHeaderTitle,
                    headerLogoPath = headerLogoPath,
                    headerBackgroundColor = headerBackgroundColor,
                    headerTextColor = headerTextColor,
                    bodyBackgroundColor = bodyBackgroundColor,
                    bodyTextColor = bodyTextColor,
                    accentColor = accentColor
                )
            }
            _uiState.update {
                it.copy(
                    deviceToken = normalizedToken,
                    deviceOwner = normalizedOwner.orEmpty(),
                    baseUrl = normalizedBaseUrl,
                    headerTitle = normalizedHeaderTitle ?: normalizedOwner.orEmpty(),
                    headerLogoPath = headerLogoPath?.takeIf { path -> path.isNotBlank() },
                    headerBackgroundColor = headerBackgroundColor,
                    headerTextColor = headerTextColor,
                    bodyBackgroundColor = bodyBackgroundColor,
                    bodyTextColor = bodyTextColor,
                    accentColor = accentColor,
                    themeColors = DeviceThemeConfig(
                        headerBackgroundColor = headerBackgroundColor,
                        headerTextColor = headerTextColor,
                        bodyBackgroundColor = bodyBackgroundColor,
                        bodyTextColor = bodyTextColor,
                        accentColor = accentColor
                    ).toVoteThemeColors(),
                    hasDeviceConfig = true
                )
            }
            _uiEvent.emit(VoteUiEvent.ConfigSaved)
            val ownerForQuestions = normalizedOwner
            if (!ownerForQuestions.isNullOrBlank()) {
                loadQuestions(owner = ownerForQuestions, token = normalizedToken)
            }
        }
    }

    fun retryFetchQuestions() {
        val state = _uiState.value
        val token = state.deviceToken
        val owner = state.deviceOwner
        if (owner.isBlank() || token.isBlank()) {
            return
        }
        loadQuestions(owner = owner, token = token)
    }

    fun scheduleAdminUnlockPrompt() {
        _uiState.update { it.copy(shouldPromptForPassword = true) }
    }

    fun clearAdminUnlockPrompt() {
        _uiState.update { it.copy(shouldPromptForPassword = false) }
    }

    override fun onCleared() {
        super.onCleared()
        inactivityJob?.cancel()
    }

    fun updateFeedbackName(value: String) {
        scheduleInactivityTimeout()
        _uiState.update { it.copy(feedbackName = value) }
    }

    fun updateFeedbackPhone(value: String) {
        scheduleInactivityTimeout()
        _uiState.update { it.copy(feedbackPhone = value) }
    }

    fun updateFeedbackEmail(value: String) {
        scheduleInactivityTimeout()
        _uiState.update { it.copy(feedbackEmail = value) }
    }

    fun updateFeedbackComment(value: String) {
        scheduleInactivityTimeout()
        _uiState.update { it.copy(feedbackComment = value) }
    }

    fun updateMarketingConsent(value: Boolean) {
        scheduleInactivityTimeout()
        _uiState.update { it.copy(marketingConsent = value) }
    }

    fun importLogo(context: Context, uri: Uri) {
        viewModelScope.launch {
            val result = withContext(ioDispatcher) {
                runCatching { copyLogoToInternal(context, uri) }
            }
            result
                .onSuccess { path ->
                    _uiState.update { it.copy(headerLogoPath = path) }
                }
                .onFailure { throwable ->
                    _uiEvent.emit(
                        VoteUiEvent.Error(
                            message = throwable.message ?: "Неуспех при зареждане на логото."
                        )
                    )
                }
        }
    }

    fun removeLogo(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(headerLogoPath = null) }
        }
    }

    fun reloadDeviceConfig() {
        refreshDeviceConfig()
    }

    private fun copyLogoToInternal(context: Context, uri: Uri): String {
        val resolver = context.contentResolver
        val logoDir = File(context.filesDir, "logos").apply { if (!exists()) mkdirs() }
        val fileName = "logo_${System.currentTimeMillis()}"
        val extension = resolver.getType(uri)?.substringAfterLast('/')?.takeIf { it.isNotBlank() } ?: "png"
        val targetFile = File(logoDir, "$fileName.$extension")
        resolver.openInputStream(uri)?.use { input ->
            targetFile.outputStream().use { output ->
                input.copyTo(output)
            }
        } ?: throw IOException("Cannot open selected image")
        return targetFile.absolutePath
    }

    private fun deleteLogoFileIfExists(path: String?) {
        if (path.isNullOrBlank()) return
        val file = File(path)
        if (file.exists()) {
            runCatching { file.delete() }
        }
    }

    companion object {
        private const val MESSAGE_TOKEN_REQUIRED = "Необходимо е да въведете токен на устройството."
        private const val MESSAGE_BASE_URL_INVALID = "API адресът е невалиден."
        private const val MESSAGE_MARKETING_CONSENT_REQUIRED =
            "Необходимо е да потвърдите съгласието си, за да продължите."
        private const val MESSAGE_ALL_QUESTIONS_REQUIRED =
            "Моля, отговорете на всички въпроси, преди да продължите."
    }
}

data class VoteUiState(
    val isLoading: Boolean = false,
    val deviceToken: String = "",
    val deviceOwner: String = "",
    val baseUrl: String = "",
    val headerTitle: String = "",
    val headerLogoPath: String? = null,
    val headerBackgroundColor: String = DeviceConfigManager.DEFAULT_HEADER_BACKGROUND_COLOR,
    val headerTextColor: String = DeviceConfigManager.DEFAULT_HEADER_TEXT_COLOR,
    val bodyBackgroundColor: String = DeviceConfigManager.DEFAULT_BODY_BACKGROUND_COLOR,
    val bodyTextColor: String = DeviceConfigManager.DEFAULT_BODY_TEXT_COLOR,
    val accentColor: String = DeviceConfigManager.DEFAULT_ACCENT_COLOR,
    val themeColors: VoteThemeColors = VoteThemeColors.Default,
    val hasDeviceConfig: Boolean = false,
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val answers: List<QuestionAnswer> = emptyList(),
    val questionsLoading: Boolean = false,
    val questionsError: String? = null,
    val isFeedbackStage: Boolean = false,
    val feedbackName: String = "",
    val feedbackPhone: String = "",
    val feedbackEmail: String = "",
    val feedbackComment: String = "",
    val marketingConsent: Boolean = false,
    val shouldPromptForPassword: Boolean = false
)

sealed interface VoteUiEvent {
    data class Success(val message: String) : VoteUiEvent
    data class Error(val message: String) : VoteUiEvent
    data object ConfigSaved : VoteUiEvent
}

data class VoteThemeColors(
    val headerBackground: Color,
    val headerText: Color,
    val bodyBackground: Color,
    val bodyText: Color,
    val accent: Color
) {
    companion object {
        val Default = VoteThemeColors(
            headerBackground = Color(0xFF101828),
            headerText = Color(0xFFFFFFFF),
            bodyBackground = Color(0xFFF8F9FC),
            bodyText = Color(0xFF1B1F3B),
            accent = Color(0xFF246BFD)
        )
    }
}

private fun DeviceThemeConfig.toVoteThemeColors(): VoteThemeColors =
    VoteThemeColors(
        headerBackground = parseComposeColor(headerBackgroundColor, VoteThemeColors.Default.headerBackground),
        headerText = parseComposeColor(headerTextColor, VoteThemeColors.Default.headerText),
        bodyBackground = parseComposeColor(bodyBackgroundColor, VoteThemeColors.Default.bodyBackground),
        bodyText = parseComposeColor(bodyTextColor, VoteThemeColors.Default.bodyText),
        accent = parseComposeColor(accentColor, VoteThemeColors.Default.accent)
    )

private fun parseComposeColor(hex: String, fallback: Color): Color =
    runCatching { Color(android.graphics.Color.parseColor(hex)) }.getOrElse { fallback }

