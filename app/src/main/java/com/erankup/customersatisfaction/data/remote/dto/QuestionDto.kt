package com.erankup.customersatisfaction.data.remote.dto

import com.google.gson.annotations.SerializedName

data class QuestionDeviceDto(
    @SerializedName("label") val label: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("token") val token: String?
)

data class QuestionDto(
    @SerializedName("_id") val id: String?,
    @SerializedName("question") val question: String?,
    @SerializedName("order") val order: Int?,
    @SerializedName("devices") val devices: List<QuestionDeviceDto>?
)

data class QuestionsResponseDto(
    val success: Boolean,
    @SerializedName("message") val message: String? = null,
    @SerializedName("msg") val questions: List<QuestionDto>?
)

