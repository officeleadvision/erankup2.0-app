package com.erankup.customersatisfaction.domain.model

data class FeedbackData(
    val name: String?,
    val phone: String?,
    val email: String?,
    val comment: String?,
    val marketingConsent: Boolean
) {
    companion object {
        val Empty = FeedbackData(
            name = null,
            phone = null,
            email = null,
            comment = null,
            marketingConsent = false
        )
    }
}

