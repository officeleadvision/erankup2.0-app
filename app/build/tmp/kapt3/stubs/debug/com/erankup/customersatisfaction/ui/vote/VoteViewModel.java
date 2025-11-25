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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u0000 C2\u00020\u0001:\u0001CB!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0019\u001a\u00020\u001aJ\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0012\u0010!\u001a\u00020\u001a2\b\u0010\"\u001a\u0004\u0018\u00010\u001cH\u0002J\u0016\u0010#\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 J\u0018\u0010$\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020\u001c2\u0006\u0010&\u001a\u00020\u001cH\u0002J\b\u0010\'\u001a\u00020\u001aH\u0014J\u000e\u0010(\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020*J\b\u0010+\u001a\u00020\u001aH\u0002J\u0006\u0010,\u001a\u00020\u001aJ\u000e\u0010-\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001eJ\b\u0010.\u001a\u00020\u001aH\u0002J\u0006\u0010/\u001a\u00020\u001aJZ\u00100\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020\u001c2\u0006\u00101\u001a\u00020\u001c2\b\u00102\u001a\u0004\u0018\u00010\u001c2\b\u00103\u001a\u0004\u0018\u00010\u001c2\u0006\u00104\u001a\u00020\u001c2\u0006\u00105\u001a\u00020\u001c2\u0006\u00106\u001a\u00020\u001c2\u0006\u00107\u001a\u00020\u001c2\u0006\u00108\u001a\u00020\u001cJ\u0006\u00109\u001a\u00020\u001aJ\b\u0010:\u001a\u00020\u001aH\u0002J\u0006\u0010;\u001a\u00020\u001aJ\u000e\u0010<\u001a\u00020\u001a2\u0006\u0010=\u001a\u00020\u001cJ\u000e\u0010>\u001a\u00020\u001a2\u0006\u0010=\u001a\u00020\u001cJ\u000e\u0010?\u001a\u00020\u001a2\u0006\u0010=\u001a\u00020\u001cJ\u000e\u0010@\u001a\u00020\u001a2\u0006\u0010=\u001a\u00020\u001cJ\u000e\u0010A\u001a\u00020\u001a2\u0006\u0010=\u001a\u00020BR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006D"}, d2 = {"Lcom/erankup/customersatisfaction/ui/vote/VoteViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/erankup/customersatisfaction/data/repository/VoteRepository;", "deviceConfigManager", "Lcom/erankup/customersatisfaction/util/DeviceConfigManager;", "ioDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Lcom/erankup/customersatisfaction/data/repository/VoteRepository;Lcom/erankup/customersatisfaction/util/DeviceConfigManager;Lkotlinx/coroutines/CoroutineDispatcher;)V", "_uiEvent", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/erankup/customersatisfaction/ui/vote/VoteUiEvent;", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/erankup/customersatisfaction/ui/vote/VoteUiState;", "inactivityJob", "Lkotlinx/coroutines/Job;", "uiEvent", "Lkotlinx/coroutines/flow/SharedFlow;", "getUiEvent", "()Lkotlinx/coroutines/flow/SharedFlow;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearAdminUnlockPrompt", "", "copyLogoToInternal", "", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "deleteLogoFileIfExists", "path", "importLogo", "loadQuestions", "owner", "token", "onCleared", "onVoteSelected", "voteType", "Lcom/erankup/customersatisfaction/domain/model/VoteType;", "refreshDeviceConfig", "reloadDeviceConfig", "removeLogo", "resetSessionDueToInactivity", "retryFetchQuestions", "saveDeviceConfig", "baseUrl", "headerTitle", "headerLogoPath", "headerBackgroundColor", "headerTextColor", "bodyBackgroundColor", "bodyTextColor", "accentColor", "scheduleAdminUnlockPrompt", "scheduleInactivityTimeout", "submitSession", "updateFeedbackComment", "value", "updateFeedbackEmail", "updateFeedbackName", "updateFeedbackPhone", "updateMarketingConsent", "", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class VoteViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.erankup.customersatisfaction.data.repository.VoteRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.erankup.customersatisfaction.util.DeviceConfigManager deviceConfigManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineDispatcher ioDispatcher = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.erankup.customersatisfaction.ui.vote.VoteUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.erankup.customersatisfaction.ui.vote.VoteUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.erankup.customersatisfaction.ui.vote.VoteUiEvent> _uiEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.erankup.customersatisfaction.ui.vote.VoteUiEvent> uiEvent = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job inactivityJob;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String MESSAGE_TOKEN_REQUIRED = "\u041d\u0435\u043e\u0431\u0445\u043e\u0434\u0438\u043c\u043e \u0435 \u0434\u0430 \u0432\u044a\u0432\u0435\u0434\u0435\u0442\u0435 \u0442\u043e\u043a\u0435\u043d \u043d\u0430 \u0443\u0441\u0442\u0440\u043e\u0439\u0441\u0442\u0432\u043e\u0442\u043e.";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String MESSAGE_BASE_URL_INVALID = "API \u0430\u0434\u0440\u0435\u0441\u044a\u0442 \u0435 \u043d\u0435\u0432\u0430\u043b\u0438\u0434\u0435\u043d.";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String MESSAGE_MARKETING_CONSENT_REQUIRED = "\u041d\u0435\u043e\u0431\u0445\u043e\u0434\u0438\u043c\u043e \u0435 \u0434\u0430 \u043f\u043e\u0442\u0432\u044a\u0440\u0434\u0438\u0442\u0435 \u0441\u044a\u0433\u043b\u0430\u0441\u0438\u0435\u0442\u043e \u0441\u0438, \u0437\u0430 \u0434\u0430 \u043f\u0440\u043e\u0434\u044a\u043b\u0436\u0438\u0442\u0435.";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String MESSAGE_ALL_QUESTIONS_REQUIRED = "\u041c\u043e\u043b\u044f, \u043e\u0442\u0433\u043e\u0432\u043e\u0440\u0435\u0442\u0435 \u043d\u0430 \u0432\u0441\u0438\u0447\u043a\u0438 \u0432\u044a\u043f\u0440\u043e\u0441\u0438, \u043f\u0440\u0435\u0434\u0438 \u0434\u0430 \u043f\u0440\u043e\u0434\u044a\u043b\u0436\u0438\u0442\u0435.";
    @org.jetbrains.annotations.NotNull()
    public static final com.erankup.customersatisfaction.ui.vote.VoteViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public VoteViewModel(@org.jetbrains.annotations.NotNull()
    com.erankup.customersatisfaction.data.repository.VoteRepository repository, @org.jetbrains.annotations.NotNull()
    com.erankup.customersatisfaction.util.DeviceConfigManager deviceConfigManager, @com.erankup.customersatisfaction.di.IoDispatcher()
    @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineDispatcher ioDispatcher) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.erankup.customersatisfaction.ui.vote.VoteUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.erankup.customersatisfaction.ui.vote.VoteUiEvent> getUiEvent() {
        return null;
    }
    
    private final void scheduleInactivityTimeout() {
    }
    
    private final void resetSessionDueToInactivity() {
    }
    
    private final void refreshDeviceConfig() {
    }
    
    private final void loadQuestions(java.lang.String owner, java.lang.String token) {
    }
    
    public final void onVoteSelected(@org.jetbrains.annotations.NotNull()
    com.erankup.customersatisfaction.domain.model.VoteType voteType) {
    }
    
    public final void submitSession() {
    }
    
    public final void saveDeviceConfig(@org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    java.lang.String owner, @org.jetbrains.annotations.NotNull()
    java.lang.String baseUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String headerTitle, @org.jetbrains.annotations.Nullable()
    java.lang.String headerLogoPath, @org.jetbrains.annotations.NotNull()
    java.lang.String headerBackgroundColor, @org.jetbrains.annotations.NotNull()
    java.lang.String headerTextColor, @org.jetbrains.annotations.NotNull()
    java.lang.String bodyBackgroundColor, @org.jetbrains.annotations.NotNull()
    java.lang.String bodyTextColor, @org.jetbrains.annotations.NotNull()
    java.lang.String accentColor) {
    }
    
    public final void retryFetchQuestions() {
    }
    
    public final void scheduleAdminUnlockPrompt() {
    }
    
    public final void clearAdminUnlockPrompt() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
    
    public final void updateFeedbackName(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateFeedbackPhone(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateFeedbackEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateFeedbackComment(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateMarketingConsent(boolean value) {
    }
    
    public final void importLogo(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    public final void removeLogo(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void reloadDeviceConfig() {
    }
    
    private final java.lang.String copyLogoToInternal(android.content.Context context, android.net.Uri uri) {
        return null;
    }
    
    private final void deleteLogoFileIfExists(java.lang.String path) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/erankup/customersatisfaction/ui/vote/VoteViewModel$Companion;", "", "()V", "MESSAGE_ALL_QUESTIONS_REQUIRED", "", "MESSAGE_BASE_URL_INVALID", "MESSAGE_MARKETING_CONSENT_REQUIRED", "MESSAGE_TOKEN_REQUIRED", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}