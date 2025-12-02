package com.erankup.customersatisfaction.domain.model

enum class VoteType(
    val remoteName: String,
    val emoji: String,
    val label: String
) {
    SUPERDISLIKE("superdislike", "\uD83D\uDE21", "Много недоволен"),
    DISLIKE("dislike", "\uD83D\uDE20", "Недоволен"),
    NEUTRAL("neutral", "\uD83D\uDE10", "Неутрален"),
    LIKE("like", "\uD83D\uDE42", "Доволен"),
    SUPERLIKE("superlike", "\uD83D\uDE04", "Много доволен");

    companion object {
        val ordered = listOf(
            SUPERLIKE,
            LIKE,
            NEUTRAL,
            DISLIKE,
            SUPERDISLIKE
        )
    }
}

