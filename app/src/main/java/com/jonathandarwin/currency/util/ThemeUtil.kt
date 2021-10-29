package com.jonathandarwin.currency.util

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeUtil {
    const val DARK_THEME = "DARK"
    const val LIGHT_THEME = "LIGHT"
    const val THEME_KEY = "SELECTED_THEME"
    const val SP = "SP"

    fun saveTheme(ctx: Context, theme: String) {
        val sharedPreference = ctx.getSharedPreferences(SP, Context.MODE_PRIVATE)
        val edit = sharedPreference.edit()
        edit.putString(THEME_KEY, theme)
        edit.apply()

    }

    fun getTheme(ctx: Context): String {
        val sharedPreference = ctx.getSharedPreferences(SP, Context.MODE_PRIVATE)

        val theme = sharedPreference.getString(THEME_KEY, "")
        return theme ?: ""
    }
}