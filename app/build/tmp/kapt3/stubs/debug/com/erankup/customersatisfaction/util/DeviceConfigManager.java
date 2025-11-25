package com.erankup.customersatisfaction.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.erankup.customersatisfaction.BuildConfig;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\nJ\u0010\u0010\u000e\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000f\u001a\u00020\nJ\u0014\u0010\u0010\u001a\u0004\u0018\u00010\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\nH\u0002J\\\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000f\u001a\u00020\n2\b\u0010\u0015\u001a\u0004\u0018\u00010\n2\b\u0010\u0016\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nJ\u001e\u0010\u001c\u001a\u00020\u001d*\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\nH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/erankup/customersatisfaction/util/DeviceConfigManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "sharedPreferences", "Landroid/content/SharedPreferences;", "clearDeviceConfig", "", "getDefaultBaseUrl", "", "getDeviceConfig", "Lcom/erankup/customersatisfaction/util/DeviceConfig;", "getEffectiveBaseUrl", "normalizeBaseUrl", "baseUrl", "normalizeColorHex", "value", "saveDeviceConfig", "token", "owner", "headerTitle", "headerLogoPath", "headerBackgroundColor", "headerTextColor", "bodyBackgroundColor", "bodyTextColor", "accentColor", "applySanitizedString", "Landroid/content/SharedPreferences$Editor;", "key", "Companion", "app_debug"})
public final class DeviceConfigManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences sharedPreferences = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFERENCES_FILE = "vote_preferences";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DEVICE_TOKEN = "device_token";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DEVICE_OWNER = "device_owner";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_BASE_URL = "api_base_url";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_HEADER_TITLE = "header_title";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_HEADER_LOGO_PATH = "header_logo_path";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_HEADER_LOGO_URL_LEGACY = "header_logo_url";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_HEADER_BACKGROUND_COLOR = "header_background_color";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_HEADER_TEXT_COLOR = "header_text_color";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_BODY_BACKGROUND_COLOR = "body_background_color";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_BODY_TEXT_COLOR = "body_text_color";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ACCENT_COLOR = "accent_color";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_HEADER_BACKGROUND_COLOR = "#101828";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_HEADER_TEXT_COLOR = "#FFFFFF";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_BODY_BACKGROUND_COLOR = "#F8F9FC";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_BODY_TEXT_COLOR = "#1B1F3B";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_ACCENT_COLOR = "#246BFD";
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex COLOR_REGEX = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.erankup.customersatisfaction.util.DeviceConfigManager.Companion Companion = null;
    
    @javax.inject.Inject()
    public DeviceConfigManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.erankup.customersatisfaction.util.DeviceConfig getDeviceConfig() {
        return null;
    }
    
    public final void saveDeviceConfig(@org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.Nullable()
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
    
    public final void clearDeviceConfig() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String normalizeBaseUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String baseUrl) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDefaultBaseUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEffectiveBaseUrl() {
        return null;
    }
    
    private final android.content.SharedPreferences.Editor applySanitizedString(android.content.SharedPreferences.Editor $this$applySanitizedString, java.lang.String key, java.lang.String value) {
        return null;
    }
    
    private final java.lang.String normalizeColorHex(java.lang.String value) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/erankup/customersatisfaction/util/DeviceConfigManager$Companion;", "", "()V", "COLOR_REGEX", "Lkotlin/text/Regex;", "DEFAULT_ACCENT_COLOR", "", "DEFAULT_BODY_BACKGROUND_COLOR", "DEFAULT_BODY_TEXT_COLOR", "DEFAULT_HEADER_BACKGROUND_COLOR", "DEFAULT_HEADER_TEXT_COLOR", "KEY_ACCENT_COLOR", "KEY_BASE_URL", "KEY_BODY_BACKGROUND_COLOR", "KEY_BODY_TEXT_COLOR", "KEY_DEVICE_OWNER", "KEY_DEVICE_TOKEN", "KEY_HEADER_BACKGROUND_COLOR", "KEY_HEADER_LOGO_PATH", "KEY_HEADER_LOGO_URL_LEGACY", "KEY_HEADER_TEXT_COLOR", "KEY_HEADER_TITLE", "PREFERENCES_FILE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}