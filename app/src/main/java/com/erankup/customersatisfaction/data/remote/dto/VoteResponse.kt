package com.erankup.customersatisfaction.data.remote.dto

data class VoteResponse(
    val success: Boolean,
    val message: String,
    val voteId: String?
)

