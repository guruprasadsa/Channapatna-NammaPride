package com.example.channapatna_namma_pride.util

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

/**
 * Manages runtime locale switching between English and Kannada.
 * Uses SharedPreferences to persist the user's language choice.
 */
object LocaleManager {

    private const val PREFS_NAME = "locale_prefs"
    private const val KEY_LANGUAGE = "selected_language"

    const val ENGLISH = "en"
    const val KANNADA = "kn"

    /** Returns the currently saved language code (defaults to English). */
    fun getLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANGUAGE, ENGLISH) ?: ENGLISH
    }

    /** Saves the selected language and returns an updated Context with the new locale. */
    fun setLocale(context: Context, languageCode: String): Context {
        persistLanguage(context, languageCode)
        return applyLocale(context, languageCode)
    }

    /** Applies the saved locale to the given context (call in attachBaseContext). */
    fun applyLocale(context: Context): Context {
        val language = getLanguage(context)
        return applyLocale(context, language)
    }

    private fun applyLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return context.createConfigurationContext(config)
    }

    private fun persistLanguage(context: Context, languageCode: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
    }

    /** Returns true if current language is Kannada. */
    fun isKannada(context: Context): Boolean = getLanguage(context) == KANNADA
}
