package com.jonathandarwin.currency.util

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeUtil {
    val DARK_THEME = "DARK"
    val LIGHT_THEME = "LIGHT"
    val THEME_KEY = "SELECTED_THEME"
    val SP = "SP"

    fun saveTheme(ctx: Context, theme:String) {
        val sp = ctx.getSharedPreferences(SP, Context.MODE_PRIVATE)
        val ed = sp.edit()
        ed.putString(THEME_KEY,theme)
        ed.apply()

    }

    fun getTheme(ctx: Context): String {
        val sp = ctx.getSharedPreferences(SP, Context.MODE_PRIVATE)
        val theme = sp.getString(THEME_KEY,"")
        return theme ?: ""
    }
    fun switchTheme(ctx:Context,toDarkMode: Boolean) {
        if (toDarkMode) {
            saveTheme(ctx, DARK_THEME)
        } else {
            saveTheme(ctx, LIGHT_THEME)
        }

    }
}