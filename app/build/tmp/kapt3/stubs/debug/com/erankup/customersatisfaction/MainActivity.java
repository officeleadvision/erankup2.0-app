package com.erankup.customersatisfaction;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.activity.ComponentActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.erankup.customersatisfaction.admin.AdminDeviceOwnerReceiver;
import com.erankup.customersatisfaction.ui.vote.VoteViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\u0006\u0010\u000b\u001a\u00020\nJ\b\u0010\f\u001a\u00020\nH\u0002J\b\u0010\r\u001a\u00020\nH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u0012\u001a\u00020\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u001a\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u001a\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\b\u0010\u0019\u001a\u00020\nH\u0014J\b\u0010\u001a\u001a\u00020\nH\u0014J\u0010\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u000fH\u0016J\u0006\u0010\u001d\u001a\u00020\nR\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u001e"}, d2 = {"Lcom/erankup/customersatisfaction/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "voteViewModel", "Lcom/erankup/customersatisfaction/ui/vote/VoteViewModel;", "getVoteViewModel", "()Lcom/erankup/customersatisfaction/ui/vote/VoteViewModel;", "voteViewModel$delegate", "Lkotlin/Lazy;", "configureLockTaskIfDeviceOwner", "", "enforceKioskMode", "handlePotentialUnlockAttempt", "hideSystemBars", "isSensitiveKey", "", "keyCode", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onKeyDown", "event", "Landroid/view/KeyEvent;", "onKeyLongPress", "onResume", "onUserLeaveHint", "onWindowFocusChanged", "hasFocus", "releaseKioskMode", "app_debug"})
public final class MainActivity extends androidx.activity.ComponentActivity {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy voteViewModel$delegate = null;
    
    public MainActivity() {
        super(0);
    }
    
    private final com.erankup.customersatisfaction.ui.vote.VoteViewModel getVoteViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onUserLeaveHint() {
    }
    
    @java.lang.Override()
    public void onWindowFocusChanged(boolean hasFocus) {
    }
    
    public final void enforceKioskMode() {
    }
    
    public final void releaseKioskMode() {
    }
    
    private final void hideSystemBars() {
    }
    
    private final void configureLockTaskIfDeviceOwner() {
    }
    
    @java.lang.Override()
    public boolean onKeyDown(int keyCode, @org.jetbrains.annotations.Nullable()
    android.view.KeyEvent event) {
        return false;
    }
    
    @java.lang.Override()
    public boolean onKeyLongPress(int keyCode, @org.jetbrains.annotations.Nullable()
    android.view.KeyEvent event) {
        return false;
    }
    
    private final boolean isSensitiveKey(int keyCode) {
        return false;
    }
    
    private final void handlePotentialUnlockAttempt() {
    }
}