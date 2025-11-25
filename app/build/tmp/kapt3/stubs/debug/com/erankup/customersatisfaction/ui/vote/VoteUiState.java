package com.erankup.customersatisfaction.ui.vote;

import android.content.Context;
import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.erankup.customersatisfaction.data.repository.VoteRepository;
import com.erankup.customersatisfaction.di.IoDispatcher;
import com.erankup.customersatisfaction.domain.model.FeedbackData;
import com.erankup.customersatisfaction.domain.model.Question;
import com.erankup.customersatisfaction.domain.model.QuestionAnswer;
import com.erankup.customersatisfaction.domain.model.VoteType;
import com.erankup.customersatisfaction.util.DeviceConfigManager;
import com.erankup.customersatisfaction.util.DeviceThemeConfig;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;
import kotlinx.coroutines.CoroutineDispatcher;
import java.io.File;
import java.io.IOException;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\bE\b\u0086\b\u0018\u00002\u00020\u0001B\u008f\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016\u0012\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0013\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0005\u0012\b\b\u0002\u0010 \u001a\u00020\u0003\u0012\b\b\u0002\u0010!\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\"J\t\u0010?\u001a\u00020\u0003H\u00c6\u0003J\t\u0010@\u001a\u00020\u0005H\u00c6\u0003J\t\u0010A\u001a\u00020\u0005H\u00c6\u0003J\t\u0010B\u001a\u00020\u0010H\u00c6\u0003J\t\u0010C\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u00c6\u0003J\t\u0010E\u001a\u00020\u0016H\u00c6\u0003J\u000f\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00180\u0013H\u00c6\u0003J\t\u0010G\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010H\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010I\u001a\u00020\u0003H\u00c6\u0003J\t\u0010J\u001a\u00020\u0005H\u00c6\u0003J\t\u0010K\u001a\u00020\u0005H\u00c6\u0003J\t\u0010L\u001a\u00020\u0005H\u00c6\u0003J\t\u0010M\u001a\u00020\u0005H\u00c6\u0003J\t\u0010N\u001a\u00020\u0005H\u00c6\u0003J\t\u0010O\u001a\u00020\u0003H\u00c6\u0003J\t\u0010P\u001a\u00020\u0003H\u00c6\u0003J\t\u0010Q\u001a\u00020\u0005H\u00c6\u0003J\t\u0010R\u001a\u00020\u0005H\u00c6\u0003J\t\u0010S\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010T\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010U\u001a\u00020\u0005H\u00c6\u0003J\t\u0010V\u001a\u00020\u0005H\u00c6\u0003J\t\u0010W\u001a\u00020\u0005H\u00c6\u0003J\u0093\u0002\u0010X\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00032\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\b\b\u0002\u0010\u0015\u001a\u00020\u00162\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00132\b\b\u0002\u0010\u0019\u001a\u00020\u00032\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u001b\u001a\u00020\u00032\b\b\u0002\u0010\u001c\u001a\u00020\u00052\b\b\u0002\u0010\u001d\u001a\u00020\u00052\b\b\u0002\u0010\u001e\u001a\u00020\u00052\b\b\u0002\u0010\u001f\u001a\u00020\u00052\b\b\u0002\u0010 \u001a\u00020\u00032\b\b\u0002\u0010!\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010Y\u001a\u00020\u00032\b\u0010Z\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010[\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\\\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010$R\u0011\u0010\f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010$R\u0011\u0010\r\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010$R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010$R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010$R\u0011\u0010\u001f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010$R\u0011\u0010\u001e\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010$R\u0011\u0010\u001c\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010$R\u0011\u0010\u001d\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010$R\u0011\u0010\u0011\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0011\u0010\n\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010$R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010$R\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010$R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010$R\u0011\u0010\u001b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u00103R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u00103R\u0011\u0010 \u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u00103R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010&R\u0013\u0010\u001a\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010$R\u0011\u0010\u0019\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u00103R\u0011\u0010!\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u00103R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010>\u00a8\u0006]"}, d2 = {"Lcom/erankup/customersatisfaction/ui/vote/VoteUiState;", "", "isLoading", "", "deviceToken", "", "deviceOwner", "baseUrl", "headerTitle", "headerLogoPath", "headerBackgroundColor", "headerTextColor", "bodyBackgroundColor", "bodyTextColor", "accentColor", "themeColors", "Lcom/erankup/customersatisfaction/ui/vote/VoteThemeColors;", "hasDeviceConfig", "questions", "", "Lcom/erankup/customersatisfaction/domain/model/Question;", "currentQuestionIndex", "", "answers", "Lcom/erankup/customersatisfaction/domain/model/QuestionAnswer;", "questionsLoading", "questionsError", "isFeedbackStage", "feedbackName", "feedbackPhone", "feedbackEmail", "feedbackComment", "marketingConsent", "shouldPromptForPassword", "(ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/erankup/customersatisfaction/ui/vote/VoteThemeColors;ZLjava/util/List;ILjava/util/List;ZLjava/lang/String;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZ)V", "getAccentColor", "()Ljava/lang/String;", "getAnswers", "()Ljava/util/List;", "getBaseUrl", "getBodyBackgroundColor", "getBodyTextColor", "getCurrentQuestionIndex", "()I", "getDeviceOwner", "getDeviceToken", "getFeedbackComment", "getFeedbackEmail", "getFeedbackName", "getFeedbackPhone", "getHasDeviceConfig", "()Z", "getHeaderBackgroundColor", "getHeaderLogoPath", "getHeaderTextColor", "getHeaderTitle", "getMarketingConsent", "getQuestions", "getQuestionsError", "getQuestionsLoading", "getShouldPromptForPassword", "getThemeColors", "()Lcom/erankup/customersatisfaction/ui/vote/VoteThemeColors;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class VoteUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String deviceToken = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String deviceOwner = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String baseUrl = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String headerTitle = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String headerLogoPath = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String headerBackgroundColor = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String headerTextColor = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String bodyBackgroundColor = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String bodyTextColor = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String accentColor = null;
    @org.jetbrains.annotations.NotNull()
    private final com.erankup.customersatisfaction.ui.vote.VoteThemeColors themeColors = null;
    private final boolean hasDeviceConfig = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.erankup.customersatisfaction.domain.model.Question> questions = null;
    private final int currentQuestionIndex = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.erankup.customersatisfaction.domain.model.QuestionAnswer> answers = null;
    private final boolean questionsLoading = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String questionsError = null;
    private final boolean isFeedbackStage = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String feedbackName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String feedbackPhone = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String feedbackEmail = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String feedbackComment = null;
    private final boolean marketingConsent = false;
    private final boolean shouldPromptForPassword = false;
    
    public VoteUiState(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceToken, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceOwner, @org.jetbrains.annotations.NotNull()
    java.lang.String baseUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String headerTitle, @org.jetbrains.annotations.Nullable()
    java.lang.String headerLogoPath, @org.jetbrains.annotations.NotNull()
    java.lang.String headerBackgroundColor, @org.jetbrains.annotations.NotNull()
    java.lang.String headerTextColor, @org.jetbrains.annotations.NotNull()
    java.lang.String bodyBackgroundColor, @org.jetbrains.annotations.NotNull()
    java.lang.String bodyTextColor, @org.jetbrains.annotations.NotNull()
    java.lang.String accentColor, @org.jetbrains.annotations.NotNull()
    com.erankup.customersatisfaction.ui.vote.VoteThemeColors themeColors, boolean hasDeviceConfig, @org.jetbrains.annotations.NotNull()
    java.util.List<com.erankup.customersatisfaction.domain.model.Question> questions, int currentQuestionIndex, @org.jetbrains.annotations.NotNull()
    java.util.List<com.erankup.customersatisfaction.domain.model.QuestionAnswer> answers, boolean questionsLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String questionsError, boolean isFeedbackStage, @org.jetbrains.annotations.NotNull()
    java.lang.String feedbackName, @org.jetbrains.annotations.NotNull()
    java.lang.String feedbackPhone, @org.jetbrains.annotations.NotNull()
    java.lang.String feedbackEmail, @org.jetbrains.annotations.NotNull()
    java.lang.String feedbackComment, boolean marketingConsent, boolean shouldPromptForPassword) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDeviceToken() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDeviceOwner() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBaseUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHeaderTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getHeaderLogoPath() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHeaderBackgroundColor() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHeaderTextColor() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBodyBackgroundColor() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBodyTextColor() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAccentColor() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.erankup.customersatisfaction.ui.vote.VoteThemeColors getThemeColors() {
        return null;
    }
    
    public final boolean getHasDeviceConfig() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.erankup.customersatisfaction.domain.model.Question> getQuestions() {
        return null;
    }
    
    public final int getCurrentQuestionIndex() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.erankup.customersatisfaction.domain.model.QuestionAnswer> getAnswers() {
        return null;
    }
    
    public final boolean getQuestionsLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getQuestionsError() {
        return null;
    }
    
    public final boolean isFeedbackStage() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFeedbackName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFeedbackPhone() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFeedbackEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFeedbackComment() {
        return null;
    }
    
    public final boolean getMarketingConsent() {
        return false;
    }
    
    public final boolean getShouldPromptForPassword() {
        return false;
    }
    
    public VoteUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.erankup.customersatisfaction.ui.vote.VoteThemeColors component12() {
        return null;
    }
    
    public final boolean component13() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.erankup.customersatisfaction.domain.model.Question> component14() {
        return null;
    }
    
    public final int component15() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.erankup.customersatisfaction.domain.model.QuestionAnswer> component16() {
        return null;
    }
    
    public final boolean component17() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component18() {
        return null;
    }
    
    public final boolean component19() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component20() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component21() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component22() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component23() {
        return null;
    }
    
    public final boolean component24() {
        return false;
    }
    
    public final boolean component25() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.erankup.customersatisfaction.ui.vote.VoteUiState copy(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceToken, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceOwner, @org.jetbrains.annotations.NotNull()
    java.lang.String baseUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String headerTitle, @org.jetbrains.annotations.Nullable()
    java.lang.String headerLogoPath, @org.jetbrains.annotations.NotNull()
    java.lang.String headerBackgroundColor, @org.jetbrains.annotations.NotNull()
    java.lang.String headerTextColor, @org.jetbrains.annotations.NotNull()
    java.lang.String bodyBackgroundColor, @org.jetbrains.annotations.NotNull()
    java.lang.String bodyTextColor, @org.jetbrains.annotations.NotNull()
    java.lang.String accentColor, @org.jetbrains.annotations.NotNull()
    com.erankup.customersatisfaction.ui.vote.VoteThemeColors themeColors, boolean hasDeviceConfig, @org.jetbrains.annotations.NotNull()
    java.util.List<com.erankup.customersatisfaction.domain.model.Question> questions, int currentQuestionIndex, @org.jetbrains.annotations.NotNull()
    java.util.List<com.erankup.customersatisfaction.domain.model.QuestionAnswer> answers, boolean questionsLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String questionsError, boolean isFeedbackStage, @org.jetbrains.annotations.NotNull()
    java.lang.String feedbackName, @org.jetbrains.annotations.NotNull()
    java.lang.String feedbackPhone, @org.jetbrains.annotations.NotNull()
    java.lang.String feedbackEmail, @org.jetbrains.annotations.NotNull()
    java.lang.String feedbackComment, boolean marketingConsent, boolean shouldPromptForPassword) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}