package com.erankup.customersatisfaction.data.repository;

import com.erankup.customersatisfaction.data.remote.VoteApiProvider;
import com.erankup.customersatisfaction.data.remote.dto.VoteRequest;
import com.erankup.customersatisfaction.data.remote.dto.FeedbackRequest;
import com.erankup.customersatisfaction.data.remote.dto.QuestionVoteItem;
import com.erankup.customersatisfaction.data.remote.dto.VoteResponse;
import com.erankup.customersatisfaction.domain.model.FeedbackData;
import com.erankup.customersatisfaction.domain.model.Question;
import com.erankup.customersatisfaction.domain.model.QuestionDevice;
import com.erankup.customersatisfaction.domain.model.QuestionAnswer;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J*\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\t\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJB\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00062\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00072\u0006\u0010\u0012\u001a\u00020\u0013H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0016"}, d2 = {"Lcom/erankup/customersatisfaction/data/repository/VoteRepositoryImpl;", "Lcom/erankup/customersatisfaction/data/repository/VoteRepository;", "voteApiProvider", "Lcom/erankup/customersatisfaction/data/remote/VoteApiProvider;", "(Lcom/erankup/customersatisfaction/data/remote/VoteApiProvider;)V", "fetchQuestions", "Lkotlin/Result;", "", "Lcom/erankup/customersatisfaction/domain/model/Question;", "owner", "", "fetchQuestions-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitSession", "Lcom/erankup/customersatisfaction/data/remote/dto/VoteResponse;", "token", "answers", "Lcom/erankup/customersatisfaction/domain/model/QuestionAnswer;", "feedbackData", "Lcom/erankup/customersatisfaction/domain/model/FeedbackData;", "submitSession-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lcom/erankup/customersatisfaction/domain/model/FeedbackData;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class VoteRepositoryImpl implements com.erankup.customersatisfaction.data.repository.VoteRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.erankup.customersatisfaction.data.remote.VoteApiProvider voteApiProvider = null;
    
    @javax.inject.Inject()
    public VoteRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.erankup.customersatisfaction.data.remote.VoteApiProvider voteApiProvider) {
        super();
    }
}