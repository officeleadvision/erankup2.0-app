package com.erankup.customersatisfaction.domain.model

data class QuestionDevice(
    val label: String?,
    val location: String?,
    val token: String?
)

data class Question(
    val id: String,
    val text: String,
    val order: Int,
    val devices: List<QuestionDevice>
)

data class QuestionAnswer(
    val questionId: String,
    val questionText: String,
    val voteType: VoteType
)

