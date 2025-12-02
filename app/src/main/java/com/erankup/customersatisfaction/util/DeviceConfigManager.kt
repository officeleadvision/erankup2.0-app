package com.erankup.customersatisfaction.util

import android.content.Context
import android.content.SharedPreferences
import com.erankup.customersatisfaction.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

data class DeviceThemeConfig(
    val headerBackgroundColor: String,
    val headerTextColor: String,
    val bodyBackgroundColor: String,
    val bodyTextColor: String,
    val accentColor: String
)

data class DeviceConfig(
    val token: String?,
    val owner: String?,
    val baseUrl: String,
    val headerTitle: String?,
    val headerLogoPath: String?,
    val questionInstruction: String?,
    val theme: DeviceThemeConfig
)

@Singleton
class DeviceConfigManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)

    fun getDeviceConfig(): DeviceConfig {
        val token = sharedPreferences.getString(KEY_DEVICE_TOKEN, null)
        val owner = sharedPreferences.getString(KEY_DEVICE_OWNER, null)
        val storedBaseUrl = sharedPreferences.getString(KEY_BASE_URL, null)
        val headerTitle = sharedPreferences.getString(KEY_HEADER_TITLE, null)
        val headerLogoPath = sharedPreferences.getString(KEY_HEADER_LOGO_PATH, null)
            ?: sharedPreferences.getString(KEY_HEADER_LOGO_URL_LEGACY, null)?.also {
                sharedPreferences.edit().remove(KEY_HEADER_LOGO_URL_LEGACY).apply()
            }
        val questionInstruction = sharedPreferences.getString(KEY_QUESTION_INSTRUCTION, null)
            ?.takeIf { it.isNotBlank() }
        val headerBgColor = sharedPreferences.getString(KEY_HEADER_BACKGROUND_COLOR, null)
        val headerTextColor = sharedPreferences.getString(KEY_HEADER_TEXT_COLOR, null)
        val bodyBgColor = sharedPreferences.getString(KEY_BODY_BACKGROUND_COLOR, null)
        val bodyTextColor = sharedPreferences.getString(KEY_BODY_TEXT_COLOR, null)
        val accentColor = sharedPreferences.getString(KEY_ACCENT_COLOR, null)
        val normalizedBaseUrl =
            storedBaseUrl?.let(::normalizeBaseUrl) ?: getDefaultBaseUrl()

        return DeviceConfig(
            token = token,
            owner = owner,
            baseUrl = normalizedBaseUrl,
            headerTitle = headerTitle?.takeIf { it.isNotBlank() },
            headerLogoPath = headerLogoPath?.takeIf { it.isNotBlank() },
            questionInstruction = questionInstruction,
            theme = DeviceThemeConfig(
                headerBackgroundColor = normalizeColorHex(headerBgColor)
                    ?: DEFAULT_HEADER_BACKGROUND_COLOR,
                headerTextColor = normalizeColorHex(headerTextColor)
                    ?: DEFAULT_HEADER_TEXT_COLOR,
                bodyBackgroundColor = normalizeColorHex(bodyBgColor)
                    ?: DEFAULT_BODY_BACKGROUND_COLOR,
                bodyTextColor = normalizeColorHex(bodyTextColor)
                    ?: DEFAULT_BODY_TEXT_COLOR,
                accentColor = normalizeColorHex(accentColor)
                    ?: DEFAULT_ACCENT_COLOR
            )
        )
    }

    fun saveDeviceConfig(
        token: String,
        owner: String?,
        baseUrl: String,
        headerTitle: String?,
        headerLogoPath: String?,
        questionInstruction: String?,
        headerBackgroundColor: String,
        headerTextColor: String,
        bodyBackgroundColor: String,
        bodyTextColor: String,
        accentColor: String
    ) {
        val normalizedBaseUrl = normalizeBaseUrl(baseUrl)
            ?: throw IllegalArgumentException("Invalid base url supplied")
        val sanitizedHeaderTitle = headerTitle?.trim().takeUnless { it.isNullOrEmpty() }
        val sanitizedLogoPath = headerLogoPath?.trim().takeUnless { it.isNullOrEmpty() }
        val sanitizedQuestionInstruction =
            questionInstruction?.trim().takeUnless { it.isNullOrEmpty() }
        val normalizedHeaderBg = normalizeColorHex(headerBackgroundColor)
            ?: throw IllegalArgumentException("Invalid header background color supplied")
        val normalizedHeaderText = normalizeColorHex(headerTextColor)
            ?: throw IllegalArgumentException("Invalid header text color supplied")
        val normalizedBodyBg = normalizeColorHex(bodyBackgroundColor)
            ?: throw IllegalArgumentException("Invalid body background color supplied")
        val normalizedBodyText = normalizeColorHex(bodyTextColor)
            ?: throw IllegalArgumentException("Invalid body text color supplied")
        val normalizedAccent = normalizeColorHex(accentColor)
            ?: throw IllegalArgumentException("Invalid accent color supplied")
        sharedPreferences.edit()
            .putString(KEY_DEVICE_TOKEN, token)
            .putString(KEY_DEVICE_OWNER, owner)
            .putString(KEY_BASE_URL, normalizedBaseUrl)
            .applySanitizedString(KEY_HEADER_TITLE, sanitizedHeaderTitle)
            .applySanitizedString(KEY_HEADER_LOGO_PATH, sanitizedLogoPath)
            .applySanitizedString(KEY_QUESTION_INSTRUCTION, sanitizedQuestionInstruction)
            .putString(KEY_HEADER_BACKGROUND_COLOR, normalizedHeaderBg)
            .putString(KEY_HEADER_TEXT_COLOR, normalizedHeaderText)
            .putString(KEY_BODY_BACKGROUND_COLOR, normalizedBodyBg)
            .putString(KEY_BODY_TEXT_COLOR, normalizedBodyText)
            .putString(KEY_ACCENT_COLOR, normalizedAccent)
            .remove(KEY_HEADER_LOGO_URL_LEGACY)
            .apply()
    }

    fun clearDeviceConfig() {
        sharedPreferences.edit()
            .remove(KEY_DEVICE_TOKEN)
            .remove(KEY_DEVICE_OWNER)
            .remove(KEY_BASE_URL)
            .remove(KEY_HEADER_TITLE)
            .remove(KEY_HEADER_LOGO_PATH)
            .remove(KEY_HEADER_LOGO_URL_LEGACY)
            .remove(KEY_QUESTION_INSTRUCTION)
            .remove(KEY_HEADER_BACKGROUND_COLOR)
            .remove(KEY_HEADER_TEXT_COLOR)
            .remove(KEY_BODY_BACKGROUND_COLOR)
            .remove(KEY_BODY_TEXT_COLOR)
            .remove(KEY_ACCENT_COLOR)
            .apply()
    }

    fun normalizeBaseUrl(baseUrl: String): String? {
        val trimmed = baseUrl.trim()
        if (trimmed.isEmpty()) return null
        val ensuredSlash = if (trimmed.endsWith("/")) trimmed else "$trimmed/"
        return ensuredSlash.toHttpUrlOrNull()?.toString()
    }

    fun getDefaultBaseUrl(): String =
        normalizeBaseUrl(BuildConfig.API_BASE_URL)
            ?: throw IllegalStateException("Invalid default base url")

    fun getEffectiveBaseUrl(): String {
        val stored = sharedPreferences.getString(KEY_BASE_URL, null)
        return stored?.let(::normalizeBaseUrl) ?: getDefaultBaseUrl()
    }

    companion object {
        private const val PREFERENCES_FILE = "vote_preferences"
        private const val KEY_DEVICE_TOKEN = "device_token"
        private const val KEY_DEVICE_OWNER = "device_owner"
        private const val KEY_BASE_URL = "api_base_url"
        private const val KEY_HEADER_TITLE = "header_title"
        private const val KEY_HEADER_LOGO_PATH = "header_logo_path"
        private const val KEY_HEADER_LOGO_URL_LEGACY = "header_logo_url"
        private const val KEY_QUESTION_INSTRUCTION = "question_instruction"
        private const val KEY_HEADER_BACKGROUND_COLOR = "header_background_color"
        private const val KEY_HEADER_TEXT_COLOR = "header_text_color"
        private const val KEY_BODY_BACKGROUND_COLOR = "body_background_color"
        private const val KEY_BODY_TEXT_COLOR = "body_text_color"
        private const val KEY_ACCENT_COLOR = "accent_color"

        const val DEFAULT_HEADER_BACKGROUND_COLOR = "#101828"
        const val DEFAULT_HEADER_TEXT_COLOR = "#FFFFFF"
        const val DEFAULT_BODY_BACKGROUND_COLOR = "#F8F9FC"
        const val DEFAULT_BODY_TEXT_COLOR = "#1B1F3B"
        const val DEFAULT_ACCENT_COLOR = "#246BFD"
        private val COLOR_REGEX = Regex("^#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{8})$")
    }

    private fun SharedPreferences.Editor.applySanitizedString(
        key: String,
        value: String?
    ): SharedPreferences.Editor {
        return if (value.isNullOrBlank()) {
            remove(key)
        } else {
            putString(key, value)
        }
    }

    private fun normalizeColorHex(value: String?): String? {
        val trimmed = value?.trim().orEmpty()
        if (trimmed.isEmpty()) return null
        val candidate = if (trimmed.startsWith("#")) trimmed else "#$trimmed"
        return candidate.uppercase().takeIf { COLOR_REGEX.matches(candidate) }
    }
}
