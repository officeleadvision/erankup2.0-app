package com.erankup.customersatisfaction.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FeedbackRequest(
    val username: String,
    val devices: String,
    val name: String?,
    val phone: String?,
    val email: String?,
    val comment: String?,
    val question: String?,
    val vote: String?,
    @SerializedName("votesList") val votes: List<QuestionVoteItem>
)

data class QuestionVoteItem(
    val question: String,
    val vote: String
)

