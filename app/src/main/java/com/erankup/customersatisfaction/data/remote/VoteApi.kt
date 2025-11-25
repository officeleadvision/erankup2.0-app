package com.erankup.customersatisfaction.data.remote

import com.erankup.customersatisfaction.data.remote.dto.FeedbackRequest
import com.erankup.customersatisfaction.data.remote.dto.QuestionsResponseDto
import com.erankup.customersatisfaction.data.remote.dto.VoteRequest
import com.erankup.customersatisfaction.data.remote.dto.VoteResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VoteApi {
    @POST("api/vote")
    suspend fun submitVote(
        @Body request: VoteRequest
    ): VoteResponse

    @GET("api/questions/{identifier}")
    suspend fun getQuestions(
        @Path("identifier") owner: String
    ): QuestionsResponseDto

    @POST("api/feedback")
    suspend fun submitFeedback(
        @Body request: FeedbackRequest
    ): retrofit2.Response<ResponseBody>
}

