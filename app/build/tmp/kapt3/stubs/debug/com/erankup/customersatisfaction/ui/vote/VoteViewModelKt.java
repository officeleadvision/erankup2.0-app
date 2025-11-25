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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0001H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0002\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\n"}, d2 = {"parseComposeColor", "Landroidx/compose/ui/graphics/Color;", "hex", "", "fallback", "parseComposeColor-4WTKRHQ", "(Ljava/lang/String;J)J", "toVoteThemeColors", "Lcom/erankup/customersatisfaction/ui/vote/VoteThemeColors;", "Lcom/erankup/customersatisfaction/util/DeviceThemeConfig;", "app_debug"})
public final class VoteViewModelKt {
    
    private static final com.erankup.customersatisfaction.ui.vote.VoteThemeColors toVoteThemeColors(com.erankup.customersatisfaction.util.DeviceThemeConfig $this$toVoteThemeColors) {
        return null;
    }
}