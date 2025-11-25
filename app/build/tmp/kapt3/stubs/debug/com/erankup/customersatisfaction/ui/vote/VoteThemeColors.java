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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 $2\u00020\u0001:\u0001$B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0011\u0010\nJ\u0016\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0013\u0010\nJ\u0016\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0015\u0010\nJ\u0016\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0017\u0010\nJ\u0016\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0019\u0010\nJE\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003H\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020#H\u00d6\u0001R\u0019\u0010\u0007\u001a\u00020\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0019\u0010\u0005\u001a\u00020\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\f\u0010\nR\u0019\u0010\u0006\u001a\u00020\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\r\u0010\nR\u0019\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\u000e\u0010\nR\u0019\u0010\u0004\u001a\u00020\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\u000f\u0010\n\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006%"}, d2 = {"Lcom/erankup/customersatisfaction/ui/vote/VoteThemeColors;", "", "headerBackground", "Landroidx/compose/ui/graphics/Color;", "headerText", "bodyBackground", "bodyText", "accent", "(JJJJJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAccent-0d7_KjU", "()J", "J", "getBodyBackground-0d7_KjU", "getBodyText-0d7_KjU", "getHeaderBackground-0d7_KjU", "getHeaderText-0d7_KjU", "component1", "component1-0d7_KjU", "component2", "component2-0d7_KjU", "component3", "component3-0d7_KjU", "component4", "component4-0d7_KjU", "component5", "component5-0d7_KjU", "copy", "copy-t635Npw", "(JJJJJ)Lcom/erankup/customersatisfaction/ui/vote/VoteThemeColors;", "equals", "", "other", "hashCode", "", "toString", "", "Companion", "app_debug"})
public final class VoteThemeColors {
    private final long headerBackground = 0L;
    private final long headerText = 0L;
    private final long bodyBackground = 0L;
    private final long bodyText = 0L;
    private final long accent = 0L;
    @org.jetbrains.annotations.NotNull()
    private static final com.erankup.customersatisfaction.ui.vote.VoteThemeColors Default = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.erankup.customersatisfaction.ui.vote.VoteThemeColors.Companion Companion = null;
    
    private VoteThemeColors(long headerBackground, long headerText, long bodyBackground, long bodyText, long accent) {
        super();
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/erankup/customersatisfaction/ui/vote/VoteThemeColors$Companion;", "", "()V", "Default", "Lcom/erankup/customersatisfaction/ui/vote/VoteThemeColors;", "getDefault", "()Lcom/erankup/customersatisfaction/ui/vote/VoteThemeColors;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.erankup.customersatisfaction.ui.vote.VoteThemeColors getDefault() {
            return null;
        }
    }
}