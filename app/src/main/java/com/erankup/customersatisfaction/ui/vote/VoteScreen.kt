package com.erankup.customersatisfaction.ui.vote

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.compose.AsyncImage
import com.erankup.customersatisfaction.MainActivity
import com.erankup.customersatisfaction.R
import com.erankup.customersatisfaction.domain.model.Question
import com.erankup.customersatisfaction.domain.model.QuestionAnswer
import com.erankup.customersatisfaction.domain.model.VoteType
import androidx.annotation.DrawableRes
import kotlinx.coroutines.delay

private const val ADMIN_PASSWORD = "LeadVision"

@Composable
fun VoteRoute(
    viewModel: VoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }
    val mainActivity = remember(activity) { activity as? MainActivity }
    val lifecycleOwner = LocalLifecycleOwner.current

    val logoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            viewModel.importLogo(context, uri)
        }
    }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showPasswordDialog by remember { mutableStateOf(false) }
    var showAdminPanel by remember { mutableStateOf(false) }
    var adminPassword by remember { mutableStateOf("") }
    var adminPasswordError by remember { mutableStateOf(false) }
    var pendingExitAfterSave by remember { mutableStateOf(false) }
    var showThankYouScreen by remember { mutableStateOf(false) }
    var thankYouMessage by remember {
        mutableStateOf(context.getString(R.string.vote_thank_you_overlay))
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                viewModel.scheduleAdminUnlockPrompt()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is VoteUiEvent.Success -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    thankYouMessage = context.getString(R.string.vote_thank_you_overlay)
                    showThankYouScreen = true
                }

                is VoteUiEvent.Error -> {
                    pendingExitAfterSave = false
                    errorMessage = event.message
                }

                VoteUiEvent.ConfigSaved -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.config_saved),
                        Toast.LENGTH_SHORT
                    ).show()
                    if (pendingExitAfterSave) {
                        pendingExitAfterSave = false
                        showAdminPanel = false
                        mainActivity?.releaseKioskMode()
                        activity?.finish()
                    } else {
                        showAdminPanel = false
                        mainActivity?.enforceKioskMode()
                    }
                }
            }
        }
    }

    LaunchedEffect(uiState.shouldPromptForPassword) {
        if (uiState.shouldPromptForPassword) {
            showPasswordDialog = true
            adminPassword = ""
            adminPasswordError = false
            viewModel.clearAdminUnlockPrompt()
        }
    }

    LaunchedEffect(showAdminPanel) {
        if (showAdminPanel) {
            mainActivity?.releaseKioskMode()
        } else {
            mainActivity?.enforceKioskMode()
        }
    }

    LaunchedEffect(showThankYouScreen) {
        if (showThankYouScreen) {
            delay(2000)
            showThankYouScreen = false
        }
    }

    BackHandler {
        when {
            errorMessage != null -> errorMessage = null
            showAdminPanel -> {
                showAdminPanel = false
                pendingExitAfterSave = false
            }
            showPasswordDialog -> {
                adminPassword = ""
                adminPasswordError = false
            }
            else -> {
                showPasswordDialog = true
                adminPassword = ""
                adminPasswordError = false
            }
        }
    }

    VoteScreen(
        state = uiState,
        onVoteSelected = viewModel::onVoteSelected,
        onSubmitSession = viewModel::submitSession,
        onRetryQuestions = viewModel::retryFetchQuestions,
        onSaveDeviceConfig = { token, owner, baseUrl, headerTitle, headerLogoPath, headerBg, headerText, bodyBg, bodyText, accent ->
            viewModel.saveDeviceConfig(
                token = token,
                owner = owner,
                baseUrl = baseUrl,
                headerTitle = headerTitle,
                headerLogoPath = headerLogoPath,
                headerBackgroundColor = headerBg,
                headerTextColor = headerText,
                bodyBackgroundColor = bodyBg,
                bodyTextColor = bodyText,
                accentColor = accent
            )
        },
        onNameChange = viewModel::updateFeedbackName,
        onPhoneChange = viewModel::updateFeedbackPhone,
        onEmailChange = viewModel::updateFeedbackEmail,
        onCommentChange = viewModel::updateFeedbackComment,
        onConsentChange = viewModel::updateMarketingConsent,
        onPickLogo = { logoPickerLauncher.launch(arrayOf("image/*")) },
        onRemoveLogo = { viewModel.removeLogo(context) },
        showThankYouScreen = showThankYouScreen,
        thankYouMessage = thankYouMessage
    )

    if (errorMessage != null) {
        AlertDialog(
            onDismissRequest = { errorMessage = null },
            confirmButton = {
                TextButton(onClick = { errorMessage = null }) {
                    Text(text = stringResource(id = R.string.dismiss))
                }
            },
            text = { Text(text = errorMessage ?: stringResource(id = R.string.vote_error)) }
        )
    }

    if (showPasswordDialog) {
        AdminPasswordDialog(
            password = adminPassword,
            isError = adminPasswordError,
            onPasswordChange = {
                adminPassword = it
                if (adminPasswordError) adminPasswordError = false
            },
            onConfirm = {
                if (adminPassword == ADMIN_PASSWORD) {
                    showPasswordDialog = false
                    adminPassword = ""
                    adminPasswordError = false
                    pendingExitAfterSave = false
                    viewModel.clearAdminUnlockPrompt()
                    showAdminPanel = true
                } else {
                    adminPasswordError = true
                }
            },
            onCancel = {
                showPasswordDialog = false
                adminPassword = ""
                adminPasswordError = false
                pendingExitAfterSave = false
                viewModel.clearAdminUnlockPrompt()
            }
        )
    }

    if (showAdminPanel) {
        AdminPanelDialog(
            initialToken = uiState.deviceToken,
            initialOwner = uiState.deviceOwner,
            initialBaseUrl = uiState.baseUrl,
            initialHeaderTitle = uiState.headerTitle,
            initialHeaderLogoPath = uiState.headerLogoPath.orEmpty(),
            initialHeaderBackgroundColor = uiState.headerBackgroundColor,
            initialHeaderTextColor = uiState.headerTextColor,
            initialBodyBackgroundColor = uiState.bodyBackgroundColor,
            initialBodyTextColor = uiState.bodyTextColor,
            initialAccentColor = uiState.accentColor,
            onSave = { token, owner, baseUrl, headerTitle, headerLogoPath, headerBg, headerText, bodyBg, bodyText, accent ->
                pendingExitAfterSave = false
                viewModel.saveDeviceConfig(token, owner, baseUrl, headerTitle, headerLogoPath, headerBg, headerText, bodyBg, bodyText, accent)
            },
            onExit = { token, owner, baseUrl, headerTitle, headerLogoPath, headerBg, headerText, bodyBg, bodyText, accent ->
                if (token.isBlank()) {
                    errorMessage = context.getString(R.string.token_required)
                } else {
                    pendingExitAfterSave = true
                    viewModel.saveDeviceConfig(token, owner, baseUrl, headerTitle, headerLogoPath, headerBg, headerText, bodyBg, bodyText, accent)
                }
            },
            onDismiss = {
                showAdminPanel = false
                pendingExitAfterSave = false
                viewModel.reloadDeviceConfig()
            },
            onPickLogo = { logoPickerLauncher.launch(arrayOf("image/*")) },
            onRemoveLogo = { viewModel.removeLogo(context) }
        )
    }
}

@Composable
private fun VoteHeader(
    title: String,
    logoPath: String?,
    colors: VoteThemeColors
) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600
    val headerBrush = remember(colors) {
        Brush.linearGradient(
            colors = listOf(
                lerp(colors.accent, colors.headerBackground, 0.35f),
                colors.headerBackground
            )
        )
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(36.dp),
        color = Color.Transparent,
        tonalElevation = 12.dp,
        shadowElevation = 12.dp
    ) {
        Box(
            modifier = Modifier
                .background(headerBrush)
                .padding(
                    horizontal = if (isTablet) 40.dp else 28.dp,
                    vertical = if (isTablet) 28.dp else 32.dp
                )
        ) {
            if (isTablet) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Box(
                        modifier = Modifier.weight(0.4f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        LogoBadge(
                            logoPath = logoPath,
                            titleFallback = title,
                            colors = colors,
                            modifier = Modifier.size(144.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(0.6f),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.5.sp
                            ),
                            color = colors.headerText,
                            textAlign = TextAlign.Start
                        )
                        Box(
                            modifier = Modifier
                                .height(4.dp)
                                .width(96.dp)
                                .clip(RoundedCornerShape(percent = 50))
                                .background(colors.headerText.copy(alpha = 0.35f))
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    LogoBadge(
                        logoPath = logoPath,
                        titleFallback = title,
                        colors = colors,
                        modifier = Modifier.size(112.dp)
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        ),
                        color = colors.headerText,
                        textAlign = TextAlign.Center
                    )
                    Box(
                        modifier = Modifier
                            .height(4.dp)
                            .width(72.dp)
                            .clip(RoundedCornerShape(percent = 50))
                            .background(colors.headerText.copy(alpha = 0.35f))
                    )
                }
            }
        }
    }
}

@Composable
private fun LogoBadge(
    logoPath: String?,
    titleFallback: String,
    colors: VoteThemeColors,
    modifier: Modifier = Modifier
    ) {
        if (!logoPath.isNullOrBlank()) {
        Surface(
            modifier = modifier,
            shape = CircleShape,
            color = colors.headerText.copy(alpha = 0.08f)
        ) {
            AsyncImage(
                model = logoPath,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    } else {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(28.dp),
            color = colors.headerText.copy(alpha = 0.1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = titleFallback,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
private fun QuestionsLoading() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CircularProgressIndicator()
        Text(
            text = stringResource(id = R.string.questions_loading),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun QuestionsError(
    message: String,
    onRetry: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.error,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.primary,
                contentColor = colors.onPrimary
            )
        ) {
            Text(text = stringResource(id = R.string.questions_retry))
        }
    }
}

@Composable
private fun QuestionStep(
    question: Question?,
    questionNumber: Int,
    totalQuestions: Int,
    onVoteSelected: (VoteType) -> Unit,
    colors: VoteThemeColors
) {
    if (question == null) {
        Text(
            text = stringResource(id = R.string.questions_missing),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = colors.bodyText,
            textAlign = TextAlign.Center
        )
        return
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(
                id = R.string.question_progress,
                questionNumber,
                totalQuestions
            ),
            style = MaterialTheme.typography.titleSmall,
            color = colors.accent
        )
        Text(
            text = question.text,
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = colors.bodyText,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.question_instruction),
            style = MaterialTheme.typography.bodyLarge,
            color = colors.bodyText,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VoteType.ordered.forEach { voteType ->
                EmojiVoteButton(
                    voteType = voteType,
                    onVoteSelected = onVoteSelected,
                    colors = colors
                )
            }
        }
    }
}

@Composable
private fun FeedbackStage(
    state: VoteUiState,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCommentChange: (String) -> Unit,
    onConsentChange: (Boolean) -> Unit,
    onSubmitSession: () -> Unit,
    colors: VoteThemeColors
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        if (state.answers.isNotEmpty()) {
            AnsweredQuestionsSummary(
                answers = state.answers,
                totalQuestions = state.questions.size,
                colors = colors
            )
        }
        FeedbackForm(
            state = state,
            onNameChange = onNameChange,
            onPhoneChange = onPhoneChange,
            onEmailChange = onEmailChange,
            onCommentChange = onCommentChange,
            onConsentChange = onConsentChange,
            showConsentWarning = !state.marketingConsent,
            colors = colors
        )
        FeedbackActions(
            isSubmitEnabled = state.marketingConsent,
            isLoading = state.isLoading,
            onSubmitSession = onSubmitSession,
            colors = colors
        )
    }
}

@Composable
private fun AnsweredQuestionsSummary(
    answers: List<QuestionAnswer>,
    totalQuestions: Int,
    colors: VoteThemeColors
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.answered_questions_title, answers.size, totalQuestions),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = colors.bodyText
        )
        answers.forEachIndexed { index, answer ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.accent.copy(alpha = 0.08f))
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.answer_question_label, index + 1, answer.questionText),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = colors.bodyText
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = answer.voteType.label,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                    color = colors.accent
                )
            }
        }
    }
}

@Composable
private fun FeedbackActions(
    isSubmitEnabled: Boolean,
    isLoading: Boolean,
    onSubmitSession: () -> Unit,
    colors: VoteThemeColors
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onSubmitSession,
            enabled = isSubmitEnabled && !isLoading,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.accent,
                contentColor = colors.headerText,
                disabledContainerColor = colors.accent.copy(alpha = 0.4f),
                disabledContentColor = colors.headerText.copy(alpha = 0.6f)
            )
        ) {
            Text(
                text = stringResource(id = R.string.feedback_submit),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        // if (!isSubmitEnabled) {
        //     ErrorHint(text = stringResource(id = R.string.feedback_consent_required_hint))
        // }
    }
}

@Composable
private fun VoteScreen(
    state: VoteUiState,
    onVoteSelected: (VoteType) -> Unit,
    onSubmitSession: () -> Unit,
    onRetryQuestions: () -> Unit,
    onSaveDeviceConfig: (
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
    ) -> Unit,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCommentChange: (String) -> Unit,
    onConsentChange: (Boolean) -> Unit,
    onPickLogo: () -> Unit,
    onRemoveLogo: () -> Unit,
    showThankYouScreen: Boolean,
    thankYouMessage: String
) {
    val themeColors = state.themeColors
    val dynamicColorScheme = MaterialTheme.colorScheme.copy(
        primary = themeColors.accent,
        secondary = themeColors.accent,
        surface = themeColors.bodyBackground,
        background = themeColors.bodyBackground,
        onPrimary = themeColors.headerText,
        onSecondary = themeColors.headerText,
        onSurface = themeColors.bodyText,
        onBackground = themeColors.bodyText
    )

    MaterialTheme(colorScheme = dynamicColorScheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = dynamicColorScheme.background
        ) {
            when {
                !state.hasDeviceConfig -> {
                    DeviceSetupScreen(
                        initialToken = state.deviceToken,
                        initialOwner = state.deviceOwner,
                        initialBaseUrl = state.baseUrl,
                        initialHeaderTitle = state.headerTitle,
                        initialHeaderLogoPath = state.headerLogoPath.orEmpty(),
                        initialHeaderBackgroundColor = state.headerBackgroundColor,
                        initialHeaderTextColor = state.headerTextColor,
                        initialBodyBackgroundColor = state.bodyBackgroundColor,
                        initialBodyTextColor = state.bodyTextColor,
                        initialAccentColor = state.accentColor,
                        onSave = onSaveDeviceConfig,
                        onPickLogo = onPickLogo,
                        onRemoveLogo = onRemoveLogo
                    )
                }

                else -> {
                    VoteContent(
                        state = state,
                        onVoteSelected = onVoteSelected,
                        onSubmitSession = onSubmitSession,
                        onRetryQuestions = onRetryQuestions,
                        onNameChange = onNameChange,
                        onPhoneChange = onPhoneChange,
                        onEmailChange = onEmailChange,
                        onCommentChange = onCommentChange,
                        onConsentChange = onConsentChange,
                        showThankYouScreen = showThankYouScreen,
                        thankYouMessage = thankYouMessage
                    )
                }
            }
        }
    }
}

@Composable
private fun DeviceSetupScreen(
    initialToken: String,
    initialOwner: String,
    initialBaseUrl: String,
    initialHeaderTitle: String,
    initialHeaderLogoPath: String,
    initialHeaderBackgroundColor: String,
    initialHeaderTextColor: String,
    initialBodyBackgroundColor: String,
    initialBodyTextColor: String,
    initialAccentColor: String,
    onSave: (
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
    ) -> Unit,
    onPickLogo: () -> Unit,
    onRemoveLogo: () -> Unit
) {
    var token by remember(initialToken) { mutableStateOf(initialToken) }
    var owner by remember(initialOwner) { mutableStateOf(initialOwner) }
    var baseUrl by remember(initialBaseUrl) { mutableStateOf(initialBaseUrl) }
    var headerTitle by remember(initialHeaderTitle) { mutableStateOf(initialHeaderTitle) }
    var headerLogoPath by remember(initialHeaderLogoPath) { mutableStateOf(initialHeaderLogoPath) }
    var headerBackgroundColor by remember(initialHeaderBackgroundColor) {
        mutableStateOf(initialHeaderBackgroundColor)
    }
    var headerTextColor by remember(initialHeaderTextColor) { mutableStateOf(initialHeaderTextColor) }
    var bodyBackgroundColor by remember(initialBodyBackgroundColor) {
        mutableStateOf(initialBodyBackgroundColor)
    }
    var bodyTextColor by remember(initialBodyTextColor) { mutableStateOf(initialBodyTextColor) }
    var accentColor by remember(initialAccentColor) { mutableStateOf(initialAccentColor) }

    var showTokenError by remember { mutableStateOf(false) }
    var showBaseUrlError by remember { mutableStateOf(false) }
    var showHeaderBgError by remember { mutableStateOf(false) }
    var showHeaderTextError by remember { mutableStateOf(false) }
    var showBodyBgError by remember { mutableStateOf(false) }
    var showBodyTextError by remember { mutableStateOf(false) }
    var showAccentError by remember { mutableStateOf(false) }

    val formScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(formScrollState)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.device_setup_title),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.device_setup_message),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = token,
            onValueChange = {
                token = it
                if (showTokenError) showTokenError = false
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.device_token_label)) },
            singleLine = true,
            isError = showTokenError
        )
        if (showTokenError) {
            Spacer(modifier = Modifier.height(8.dp))
            ErrorHint(text = stringResource(id = R.string.token_required))
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = baseUrl,
            onValueChange = {
                baseUrl = it
                if (showBaseUrlError) showBaseUrlError = false
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.api_base_url_label)) },
            placeholder = { Text(text = stringResource(id = R.string.base_url_placeholder)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
            singleLine = true,
            isError = showBaseUrlError
        )
        if (showBaseUrlError) {
            Spacer(modifier = Modifier.height(8.dp))
            ErrorHint(text = stringResource(id = R.string.base_url_required))
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = owner,
            onValueChange = { owner = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.device_owner_label)) },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = headerTitle,
            onValueChange = { headerTitle = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.header_title_label)) },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = headerLogoPath,
            onValueChange = { headerLogoPath = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.header_logo_url_label)) },
            placeholder = { Text(text = stringResource(id = R.string.header_logo_url_placeholder)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        LogoPreviewSection(
            logoPath = headerLogoPath,
            onPickLogo = onPickLogo,
            onRemoveLogo = {
                headerLogoPath = ""
                onRemoveLogo()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ColorInputField(
            value = headerBackgroundColor,
            label = stringResource(id = R.string.header_background_color_label),
            isError = showHeaderBgError,
            onValueChange = {
                headerBackgroundColor = it
                if (showHeaderBgError) showHeaderBgError = false
            }
        )
        if (showHeaderBgError) {
            ErrorHint(text = stringResource(id = R.string.color_invalid))
        }
        Spacer(modifier = Modifier.height(16.dp))
        ColorInputField(
            value = headerTextColor,
            label = stringResource(id = R.string.header_text_color_label),
            isError = showHeaderTextError,
            onValueChange = {
                headerTextColor = it
                if (showHeaderTextError) showHeaderTextError = false
            }
        )
        if (showHeaderTextError) {
            ErrorHint(text = stringResource(id = R.string.color_invalid))
        }
        Spacer(modifier = Modifier.height(16.dp))
        ColorInputField(
            value = bodyBackgroundColor,
            label = stringResource(id = R.string.body_background_color_label),
            isError = showBodyBgError,
            onValueChange = {
                bodyBackgroundColor = it
                if (showBodyBgError) showBodyBgError = false
            }
        )
        if (showBodyBgError) {
            ErrorHint(text = stringResource(id = R.string.color_invalid))
        }
        Spacer(modifier = Modifier.height(16.dp))
        ColorInputField(
            value = bodyTextColor,
            label = stringResource(id = R.string.body_text_color_label),
            isError = showBodyTextError,
            onValueChange = {
                bodyTextColor = it
                if (showBodyTextError) showBodyTextError = false
            }
        )
        if (showBodyTextError) {
            ErrorHint(text = stringResource(id = R.string.color_invalid))
        }
        Spacer(modifier = Modifier.height(16.dp))
        ColorInputField(
            value = accentColor,
            label = stringResource(id = R.string.accent_color_label),
            isError = showAccentError,
            onValueChange = {
                accentColor = it
                if (showAccentError) showAccentError = false
            }
        )
        if (showAccentError) {
            ErrorHint(text = stringResource(id = R.string.color_invalid))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                val trimmedToken = token.trim()
                val trimmedBaseUrl = baseUrl.trim()
                val trimmedOwner = owner.trim()
                val trimmedHeaderTitle = headerTitle.trim().ifEmpty { null }
                val trimmedLogoUrl = headerLogoPath.trim().ifEmpty { null }
                var hasError = false
                if (trimmedToken.isEmpty()) {
                    showTokenError = true
                    hasError = true
                }
                if (trimmedBaseUrl.isEmpty()) {
                    showBaseUrlError = true
                    hasError = true
                }
                val normalizedHeaderBg = normalizeColorInput(headerBackgroundColor)
                val normalizedHeaderText = normalizeColorInput(headerTextColor)
                val normalizedBodyBg = normalizeColorInput(bodyBackgroundColor)
                val normalizedBodyText = normalizeColorInput(bodyTextColor)
                val normalizedAccent = normalizeColorInput(accentColor)
                if (normalizedHeaderBg == null) {
                    showHeaderBgError = true
                    hasError = true
                }
                if (normalizedHeaderText == null) {
                    showHeaderTextError = true
                    hasError = true
                }
                if (normalizedBodyBg == null) {
                    showBodyBgError = true
                    hasError = true
                }
                if (normalizedBodyText == null) {
                    showBodyTextError = true
                    hasError = true
                }
                if (normalizedAccent == null) {
                    showAccentError = true
                    hasError = true
                }
                if (!hasError) {
                    onSave(
                        trimmedToken,
                        trimmedOwner,
                        trimmedBaseUrl,
                        trimmedHeaderTitle,
                        headerLogoPath.takeIf { it.isNotBlank() },
                        normalizedHeaderBg!!,
                        normalizedHeaderText!!,
                        normalizedBodyBg!!,
                        normalizedBodyText!!,
                        normalizedAccent!!
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

@Composable
private fun VoteContent(
    state: VoteUiState,
    onVoteSelected: (VoteType) -> Unit,
    onSubmitSession: () -> Unit,
    onRetryQuestions: () -> Unit,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCommentChange: (String) -> Unit,
    onConsentChange: (Boolean) -> Unit,
    showThankYouScreen: Boolean,
    thankYouMessage: String
) {
    val colors = state.themeColors
    val verticalScrollState = rememberScrollState()
    val headerGradient = remember(colors) {
        Brush.verticalGradient(
            colors = listOf(
                lerp(colors.accent, colors.headerBackground, 0.25f),
                colors.headerBackground,
                colors.bodyBackground
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.bodyBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(headerGradient)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(verticalScrollState)
                .padding(horizontal = 12.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            VoteHeader(
                title = state.headerTitle.ifBlank {
                    stringResource(id = R.string.header_default_title)
                },
                logoPath = state.headerLogoPath,
                colors = colors
            )
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 3.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
            when {
                state.questionsLoading -> QuestionsLoading()

                state.questionsError != null -> QuestionsError(
                    message = state.questionsError,
                    onRetry = onRetryQuestions
                )

                !state.isFeedbackStage -> QuestionStep(
                    question = state.questions.getOrNull(state.currentQuestionIndex),
                    questionNumber = state.currentQuestionIndex + 1,
                    totalQuestions = state.questions.size,
                    onVoteSelected = onVoteSelected,
                    colors = colors
                )

                else -> FeedbackStage(
                    state = state,
                    onNameChange = onNameChange,
                    onPhoneChange = onPhoneChange,
                    onEmailChange = onEmailChange,
                    onCommentChange = onCommentChange,
                    onConsentChange = onConsentChange,
                    onSubmitSession = onSubmitSession,
                    colors = colors
                )
            }
                }
            }
        }

        AnimatedVisibility(
            visible = showThankYouScreen,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ThankYouScreen(
                message = thankYouMessage,
                colors = colors
            )
        }

        AnimatedVisibility(
            visible = state.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colors.accent)
            }
        }
    }
}

@Composable
private fun ThankYouScreen(
    message: String,
    colors: VoteThemeColors
) {
    val overlayBrush = remember(colors) {
        Brush.verticalGradient(
            colors = listOf(
                colors.accent.copy(alpha = 0.98f),
                lerp(colors.accent, colors.headerBackground, 0.25f),
                colors.headerBackground.copy(alpha = 0.95f)
            )
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(overlayBrush),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = colors.headerText.copy(alpha = 0.18f)
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp
                        ),
                        color = colors.headerText
                    )
                }
            }
            Text(
                text = message,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                ),
                color = colors.headerText,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.vote_thank_you_subtitle),
                style = MaterialTheme.typography.bodyLarge.copy(letterSpacing = 0.5.sp),
                color = colors.headerText.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun EmojiVoteButton(
    voteType: VoteType,
    onVoteSelected: (VoteType) -> Unit,
    colors: VoteThemeColors,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(116.dp)
                .clip(CircleShape)
                .clickable { onVoteSelected(voteType) },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = voteType.drawableRes()),
                contentDescription = voteType.label,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = voteType.label,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            color = colors.bodyText
        )
    }
}

@DrawableRes
private fun VoteType.drawableRes(): Int = when (this) {
    VoteType.SUPERDISLIKE -> R.drawable.ic_vote_superdislike
    VoteType.DISLIKE -> R.drawable.ic_vote_dislike
    VoteType.NEUTRAL -> R.drawable.ic_vote_neutral
    VoteType.LIKE -> R.drawable.ic_vote_like
    VoteType.SUPERLIKE -> R.drawable.ic_vote_superlike
}

@Composable
private fun FeedbackForm(
    state: VoteUiState,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCommentChange: (String) -> Unit,
    onConsentChange: (Boolean) -> Unit,
    showConsentWarning: Boolean,
    colors: VoteThemeColors
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = stringResource(id = R.string.feedback_heading),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = colors.bodyText
        )
        OutlinedTextField(
            value = state.feedbackName,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.name_label)) },
            singleLine = true
        )
        OutlinedTextField(
            value = state.feedbackPhone,
            onValueChange = onPhoneChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.phone_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true
        )
        OutlinedTextField(
            value = state.feedbackEmail,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.email_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )
        OutlinedTextField(
            value = state.feedbackComment,
            onValueChange = onCommentChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            label = { Text(text = stringResource(id = R.string.comment_label)) },
            keyboardOptions = KeyboardOptions.Default,
            maxLines = 6
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(
                checked = state.marketingConsent,
                onCheckedChange = onConsentChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = colors.accent,
                    uncheckedColor = colors.accent.copy(alpha = 0.6f),
                    checkmarkColor = colors.headerText
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.marketing_consent_text),
                style = MaterialTheme.typography.bodySmall,
                color = colors.bodyText
            )
        }
        if (showConsentWarning) {
            ErrorHint(text = stringResource(id = R.string.feedback_consent_required_hint))
        }
    }
}

@Composable
private fun AdminPasswordDialog(
    password: String,
    isError: Boolean,
    onPasswordChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = stringResource(id = R.string.exit_prompt_title)) },
        text = {
            Column {
                Text(text = stringResource(id = R.string.admin_password_prompt))
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    modifier = Modifier.fillMaxWidth(),
                    isError = isError,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    placeholder = { Text(text = stringResource(id = R.string.password_placeholder)) }
                )
                if (isError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.invalid_password),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(id = R.string.continue_label))
            }
        }
    )
}

@Composable
private fun AdminPanelDialog(
    initialToken: String,
    initialOwner: String,
    initialBaseUrl: String,
    initialHeaderTitle: String,
    initialHeaderLogoPath: String,
    initialHeaderBackgroundColor: String,
    initialHeaderTextColor: String,
    initialBodyBackgroundColor: String,
    initialBodyTextColor: String,
    initialAccentColor: String,
    onSave: (
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
    ) -> Unit,
    onExit: (
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
    ) -> Unit,
    onDismiss: () -> Unit,
    onPickLogo: () -> Unit,
    onRemoveLogo: () -> Unit
) {
    var token by remember(initialToken) { mutableStateOf(initialToken) }
    var owner by remember(initialOwner) { mutableStateOf(initialOwner) }
    var baseUrl by remember(initialBaseUrl) { mutableStateOf(initialBaseUrl) }
    var headerTitle by remember(initialHeaderTitle) { mutableStateOf(initialHeaderTitle) }
    var headerLogoPath by remember(initialHeaderLogoPath) { mutableStateOf(initialHeaderLogoPath) }
    var headerBackgroundColor by remember(initialHeaderBackgroundColor) {
        mutableStateOf(initialHeaderBackgroundColor)
    }
    var headerTextColor by remember(initialHeaderTextColor) { mutableStateOf(initialHeaderTextColor) }
    var bodyBackgroundColor by remember(initialBodyBackgroundColor) {
        mutableStateOf(initialBodyBackgroundColor)
    }
    var bodyTextColor by remember(initialBodyTextColor) { mutableStateOf(initialBodyTextColor) }
    var accentColor by remember(initialAccentColor) { mutableStateOf(initialAccentColor) }

    var showTokenError by remember { mutableStateOf(false) }
    var showBaseUrlError by remember { mutableStateOf(false) }
    var showHeaderBgError by remember { mutableStateOf(false) }
    var showHeaderTextError by remember { mutableStateOf(false) }
    var showBodyBgError by remember { mutableStateOf(false) }
    var showBodyTextError by remember { mutableStateOf(false) }
    var showAccentError by remember { mutableStateOf(false) }

    val dialogScrollState = rememberScrollState()

    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.admin_panel_title)) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 520.dp)
                    .verticalScroll(dialogScrollState),
                verticalArrangement = Arrangement.Top
            ) {
                Text(text = stringResource(id = R.string.admin_panel_message))
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = token,
                    onValueChange = {
                        token = it
                        if (showTokenError) showTokenError = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.device_token_label)) },
                    singleLine = true,
                    isError = showTokenError
                )
                if (showTokenError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    ErrorHint(text = stringResource(id = R.string.token_required))
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = baseUrl,
                    onValueChange = {
                        baseUrl = it
                        if (showBaseUrlError) showBaseUrlError = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.api_base_url_label)) },
                    placeholder = { Text(text = stringResource(id = R.string.base_url_placeholder)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                    singleLine = true,
                    isError = showBaseUrlError
                )
                if (showBaseUrlError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    ErrorHint(text = stringResource(id = R.string.base_url_required))
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = owner,
                    onValueChange = { owner = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.device_owner_label)) },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = headerTitle,
                    onValueChange = { headerTitle = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.header_title_label)) },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = headerLogoPath,
                    onValueChange = { headerLogoPath = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.header_logo_url_label)) },
                    placeholder = { Text(text = stringResource(id = R.string.header_logo_url_placeholder)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                LogoPreviewSection(
                    logoPath = headerLogoPath,
                    onPickLogo = onPickLogo,
                    onRemoveLogo = {
                        headerLogoPath = ""
                        onRemoveLogo()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ColorInputField(
                    value = headerBackgroundColor,
                    label = stringResource(id = R.string.header_background_color_label),
                    isError = showHeaderBgError,
                    onValueChange = {
                        headerBackgroundColor = it
                        if (showHeaderBgError) showHeaderBgError = false
                    }
                )
                if (showHeaderBgError) {
                    ErrorHint(text = stringResource(id = R.string.color_invalid))
                }
                Spacer(modifier = Modifier.height(16.dp))
                ColorInputField(
                    value = headerTextColor,
                    label = stringResource(id = R.string.header_text_color_label),
                    isError = showHeaderTextError,
                    onValueChange = {
                        headerTextColor = it
                        if (showHeaderTextError) showHeaderTextError = false
                    }
                )
                if (showHeaderTextError) {
                    ErrorHint(text = stringResource(id = R.string.color_invalid))
                }
                Spacer(modifier = Modifier.height(16.dp))
                ColorInputField(
                    value = bodyBackgroundColor,
                    label = stringResource(id = R.string.body_background_color_label),
                    isError = showBodyBgError,
                    onValueChange = {
                        bodyBackgroundColor = it
                        if (showBodyBgError) showBodyBgError = false
                    }
                )
                if (showBodyBgError) {
                    ErrorHint(text = stringResource(id = R.string.color_invalid))
                }
                Spacer(modifier = Modifier.height(16.dp))
                ColorInputField(
                    value = bodyTextColor,
                    label = stringResource(id = R.string.body_text_color_label),
                    isError = showBodyTextError,
                    onValueChange = {
                        bodyTextColor = it
                        if (showBodyTextError) showBodyTextError = false
                    }
                )
                if (showBodyTextError) {
                    ErrorHint(text = stringResource(id = R.string.color_invalid))
                }
                Spacer(modifier = Modifier.height(16.dp))
                ColorInputField(
                    value = accentColor,
                    label = stringResource(id = R.string.accent_color_label),
                    isError = showAccentError,
                    onValueChange = {
                        accentColor = it
                        if (showAccentError) showAccentError = false
                    }
                )
                if (showAccentError) {
                    ErrorHint(text = stringResource(id = R.string.color_invalid))
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val trimmedToken = token.trim()
                val trimmedBaseUrl = baseUrl.trim()
                val trimmedOwner = owner.trim()
                val trimmedHeaderTitle = headerTitle.trim().ifEmpty { null }
                val trimmedLogoUrl = headerLogoPath.trim().ifEmpty { null }
                var hasError = false
                if (trimmedToken.isEmpty()) {
                    showTokenError = true
                    hasError = true
                }
                if (trimmedBaseUrl.isEmpty()) {
                    showBaseUrlError = true
                    hasError = true
                }
                val normalizedHeaderBg = normalizeColorInput(headerBackgroundColor)
                val normalizedHeaderText = normalizeColorInput(headerTextColor)
                val normalizedBodyBg = normalizeColorInput(bodyBackgroundColor)
                val normalizedBodyText = normalizeColorInput(bodyTextColor)
                val normalizedAccent = normalizeColorInput(accentColor)
                if (normalizedHeaderBg == null) {
                    showHeaderBgError = true
                    hasError = true
                }
                if (normalizedHeaderText == null) {
                    showHeaderTextError = true
                    hasError = true
                }
                if (normalizedBodyBg == null) {
                    showBodyBgError = true
                    hasError = true
                }
                if (normalizedBodyText == null) {
                    showBodyTextError = true
                    hasError = true
                }
                if (normalizedAccent == null) {
                    showAccentError = true
                    hasError = true
                }
                if (!hasError) {
                    onSave(
                        trimmedToken,
                        trimmedOwner,
                        trimmedBaseUrl,
                        trimmedHeaderTitle,
                        trimmedLogoUrl,
                        normalizedHeaderBg!!,
                        normalizedHeaderText!!,
                        normalizedBodyBg!!,
                        normalizedBodyText!!,
                        normalizedAccent!!
                    )
                }
            }) {
                Text(text = stringResource(id = R.string.save_changes))
            }
        },
        dismissButton = {
            Column(horizontalAlignment = Alignment.End) {
                TextButton(onClick = onDismiss) {
                    Text(text = stringResource(id = R.string.close))
                }
                TextButton(onClick = {
                    val trimmedToken = token.trim()
                    val trimmedBaseUrl = baseUrl.trim()
                    val trimmedOwner = owner.trim()
                    val trimmedHeaderTitle = headerTitle.trim().ifEmpty { null }
                    val trimmedLogoUrl = headerLogoPath.trim().ifEmpty { null }
                    var hasError = false
                    if (trimmedToken.isEmpty()) {
                        showTokenError = true
                        hasError = true
                    }
                    if (trimmedBaseUrl.isEmpty()) {
                        showBaseUrlError = true
                        hasError = true
                    }
                    val normalizedHeaderBg = normalizeColorInput(headerBackgroundColor)
                    val normalizedHeaderText = normalizeColorInput(headerTextColor)
                    val normalizedBodyBg = normalizeColorInput(bodyBackgroundColor)
                    val normalizedBodyText = normalizeColorInput(bodyTextColor)
                    val normalizedAccent = normalizeColorInput(accentColor)
                    if (normalizedHeaderBg == null) {
                        showHeaderBgError = true
                        hasError = true
                    }
                    if (normalizedHeaderText == null) {
                        showHeaderTextError = true
                        hasError = true
                    }
                    if (normalizedBodyBg == null) {
                        showBodyBgError = true
                        hasError = true
                    }
                    if (normalizedBodyText == null) {
                        showBodyTextError = true
                        hasError = true
                    }
                    if (normalizedAccent == null) {
                        showAccentError = true
                        hasError = true
                    }
                    if (!hasError) {
                        onExit(
                            trimmedToken,
                            trimmedOwner,
                            trimmedBaseUrl,
                            trimmedHeaderTitle,
                            trimmedLogoUrl,
                            normalizedHeaderBg!!,
                            normalizedHeaderText!!,
                            normalizedBodyBg!!,
                            normalizedBodyText!!,
                            normalizedAccent!!
                        )
                    }
                }) {
                    Text(text = stringResource(id = R.string.exit_app))
                }
            }
        }
    )
}

@Composable
private fun ErrorHint(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ColorInputField(
    value: String,
    label: String,
    isError: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label) },
        placeholder = { Text(text = stringResource(id = R.string.color_hex_hint)) },
        singleLine = true,
        isError = isError,
        trailingIcon = {
            parsedColorOrNull(value)?.let { previewColor ->
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(previewColor)
                )
            }
        }
    )
}

private val HexColorRegex = Regex("^#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{8})$")

private fun normalizeColorInput(value: String): String? {
    val trimmed = value.trim()
    if (trimmed.isEmpty()) return null
    val candidate = if (trimmed.startsWith("#")) trimmed else "#${trimmed}"
    return candidate.uppercase().takeIf { HexColorRegex.matches(candidate) }
}

private fun parsedColorOrNull(value: String): Color? =
    normalizeColorInput(value)?.let { hex ->
        runCatching { Color(android.graphics.Color.parseColor(hex)) }.getOrNull()
    }

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
private fun LogoPreviewSection(
    logoPath: String,
    onPickLogo: () -> Unit,
    onRemoveLogo: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.logo_section_title),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
        val previewPath = logoPath.takeIf { it.isNotBlank() }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (previewPath != null) {
                AsyncImage(
                    model = previewPath,
                    contentDescription = null,
                    modifier = Modifier
                        .size(88.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.logo_not_selected),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(onClick = onPickLogo) {
                    Text(text = stringResource(id = R.string.logo_pick_button))
                }
                if (previewPath != null) {
                    TextButton(onClick = onRemoveLogo) {
                        Text(text = stringResource(id = R.string.logo_remove_button))
                    }
                }
                previewPath?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
