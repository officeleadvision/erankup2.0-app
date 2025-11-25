package com.erankup.customersatisfaction.data.remote.dto

data class VoteRequest(
    val token: String,
    val vote: String,
    val questionText: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val comment: String? = null,
    val marketingConsent: Boolean? = null
)

