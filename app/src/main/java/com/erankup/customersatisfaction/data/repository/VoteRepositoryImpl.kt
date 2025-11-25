package com.erankup.customersatisfaction.data.repository

import com.erankup.customersatisfaction.data.remote.VoteApiProvider
import com.erankup.customersatisfaction.data.remote.dto.VoteRequest
import com.erankup.customersatisfaction.data.remote.dto.FeedbackRequest
import com.erankup.customersatisfaction.data.remote.dto.QuestionVoteItem
import com.erankup.customersatisfaction.data.remote.dto.VoteResponse
import com.erankup.customersatisfaction.domain.model.FeedbackData
import com.erankup.customersatisfaction.domain.model.Question
import com.erankup.customersatisfaction.domain.model.QuestionDevice
import com.erankup.customersatisfaction.domain.model.QuestionAnswer
import javax.inject.Inject

class VoteRepositoryImpl @Inject constructor(
    private val voteApiProvider: VoteApiProvider
) : VoteRepository {

    override suspend fun fetchQuestions(owner: String): Result<List<Question>> {
        return runCatching {
            val api = voteApiProvider.getApi()
            val response = api.getQuestions(owner = owner)
            if (!response.success) {
                throw IllegalStateException(
                    response.message ?: "Неуспех при зареждане на въпросите."
                )
            }
            response.questions.orEmpty()
                .mapNotNull { dto ->
                    val id = dto.id ?: return@mapNotNull null
                    val text = dto.question ?: return@mapNotNull null
                    val order = dto.order ?: Int.MAX_VALUE
                    val devices = dto.devices.orEmpty()
                        .map { deviceDto ->
                            QuestionDevice(
                                label = deviceDto.label,
                                location = deviceDto.location
                            )
                        }
                    Question(
                        id = id,
                        text = text,
                        order = order,
                        devices = devices
                    )
                }
                .sortedBy { it.order }
        }
    }

    override suspend fun submitSession(
        token: String,
        owner: String,
        answers: List<QuestionAnswer>,
        feedbackData: FeedbackData
    ): Result<VoteResponse> {
        return runCatching {
            if (answers.isEmpty()) {
                throw IllegalStateException("Липсват отговори за подаване.")
            }
            val api = voteApiProvider.getApi()
            val primaryAnswer = answers.first()

            val voteResponse = api.submitVote(
                VoteRequest(
                    token = token,
                    vote = primaryAnswer.voteType.remoteName,
                    name = feedbackData.name,
                    phone = feedbackData.phone,
                    email = feedbackData.email,
                    comment = feedbackData.comment,
                    marketingConsent = feedbackData.marketingConsent,
                    questionText = primaryAnswer.questionText
                )
            )
            if (!voteResponse.success) {
                throw IllegalStateException(voteResponse.message)
            }

            val votesPayload = answers.map {
                QuestionVoteItem(
                    question = it.questionText,
                    vote = it.voteType.remoteName
                )
            }

            val feedbackRequest = FeedbackRequest(
                username = owner,
                devices = token,
                name = feedbackData.name,
                phone = feedbackData.phone,
                email = feedbackData.email,
                comment = feedbackData.comment,
                question = primaryAnswer.questionText,
                vote = primaryAnswer.voteType.remoteName,
                votes = votesPayload
            )

            val feedbackResponse = api.submitFeedback(feedbackRequest)
            if (!feedbackResponse.isSuccessful) {
                val errorBody = feedbackResponse.errorBody()?.string()
                throw IllegalStateException(
                    errorBody?.ifBlank { null }
                        ?: "Неуспех при изпращане на подробната обратна връзка."
                )
            }

            val feedbackMessage = feedbackResponse.body()?.string().orEmpty()
            val summaryMessage = feedbackMessage.ifBlank {
                voteResponse.message.ifBlank { "Обратната връзка е записана успешно." }
            }
            VoteResponse(
                success = true,
                message = summaryMessage,
                voteId = voteResponse.voteId
            )
        }
    }
}

