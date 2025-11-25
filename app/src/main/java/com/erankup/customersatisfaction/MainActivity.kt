package com.erankup.customersatisfaction

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.content.getSystemService
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.erankup.customersatisfaction.admin.AdminDeviceOwnerReceiver
import com.erankup.customersatisfaction.ui.theme.CustomerSatisfactionTheme
import com.erankup.customersatisfaction.ui.vote.VoteRoute
import com.erankup.customersatisfaction.ui.vote.VoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val voteViewModel: VoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        configureLockTaskIfDeviceOwner()
        enforceKioskMode()
        setContent {
            CustomerSatisfactionTheme {
                VoteRoute(viewModel = voteViewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        enforceKioskMode()
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        voteViewModel.scheduleAdminUnlockPrompt()
        enforceKioskMode()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemBars()
        }
    }

    fun enforceKioskMode() {
        hideSystemBars()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            try {
                startLockTask()
            } catch (_: IllegalStateException) {
                // Lock task might already be active or not permitted; ignore.
            }
        }
    }

    fun releaseKioskMode() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            try {
                stopLockTask()
            } catch (_: IllegalStateException) {
                // Already released or not permitted.
            }
        }
    }

    private fun hideSystemBars() {
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun configureLockTaskIfDeviceOwner() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            return
        }
        val dpm = getSystemService<DevicePolicyManager>() ?: return
        val admin = ComponentName(this, AdminDeviceOwnerReceiver::class.java)
        if (dpm.isDeviceOwnerApp(packageName)) {
            dpm.setLockTaskPackages(admin, arrayOf(packageName))
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_APP_SWITCH) {
            handlePotentialUnlockAttempt()
            return true
        }
        if (event != null && isSensitiveKey(keyCode)) {
            if (event.repeatCount == 0) {
                event.startTracking()
            } else if (event.isLongPress) {
                handlePotentialUnlockAttempt()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        if (isSensitiveKey(keyCode)) {
            handlePotentialUnlockAttempt()
            return true
        }
        return super.onKeyLongPress(keyCode, event)
    }

    private fun isSensitiveKey(keyCode: Int): Boolean {
        return keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_APP_SWITCH
    }

    private fun handlePotentialUnlockAttempt() {
        voteViewModel.scheduleAdminUnlockPrompt()
        enforceKioskMode()
    }
}

