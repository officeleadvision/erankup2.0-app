package com.erankup.customersatisfaction.data.repository

import com.erankup.customersatisfaction.domain.model.FeedbackData
import com.erankup.customersatisfaction.domain.model.Question
import com.erankup.customersatisfaction.domain.model.QuestionAnswer
import com.erankup.customersatisfaction.domain.model.VoteType
import com.erankup.customersatisfaction.data.remote.dto.VoteResponse

interface VoteRepository {
    suspend fun fetchQuestions(
        owner: String
    ): Result<List<Question>>

    suspend fun submitSession(
        token: String,
        owner: String,
        answers: List<QuestionAnswer>,
        feedbackData: FeedbackData
    ): Result<VoteResponse>
}

