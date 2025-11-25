package com.erankup.customersatisfaction.data.remote;

import com.erankup.customersatisfaction.data.remote.dto.FeedbackRequest;
import com.erankup.customersatisfaction.data.remote.dto.QuestionsResponseDto;
import com.erankup.customersatisfaction.data.remote.dto.VoteRequest;
import com.erankup.customersatisfaction.data.remote.dto.VoteResponse;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0001\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0018\u0010\r\u001a\u00020\u000e2\b\b\u0001\u0010\n\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/erankup/customersatisfaction/data/remote/VoteApi;", "", "getQuestions", "Lcom/erankup/customersatisfaction/data/remote/dto/QuestionsResponseDto;", "owner", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitFeedback", "Lretrofit2/Response;", "Lokhttp3/ResponseBody;", "request", "Lcom/erankup/customersatisfaction/data/remote/dto/FeedbackRequest;", "(Lcom/erankup/customersatisfaction/data/remote/dto/FeedbackRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitVote", "Lcom/erankup/customersatisfaction/data/remote/dto/VoteResponse;", "Lcom/erankup/customersatisfaction/data/remote/dto/VoteRequest;", "(Lcom/erankup/customersatisfaction/data/remote/dto/VoteRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface VoteApi {
    
    @retrofit2.http.POST(value = "api/vote")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object submitVote(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.erankup.customersatisfaction.data.remote.dto.VoteRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.erankup.customersatisfaction.data.remote.dto.VoteResponse> $completion);
    
    @retrofit2.http.GET(value = "api/questions/{identifier}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuestions(@retrofit2.http.Path(value = "identifier")
    @org.jetbrains.annotations.NotNull()
    java.lang.String owner, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.erankup.customersatisfaction.data.remote.dto.QuestionsResponseDto> $completion);
    
    @retrofit2.http.POST(value = "api/feedback")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object submitFeedback(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.erankup.customersatisfaction.data.remote.dto.FeedbackRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<okhttp3.ResponseBody>> $completion);
}