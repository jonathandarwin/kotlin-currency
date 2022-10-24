package com.jonathandarwin.currency.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.jonathandarwin.currency.R
import com.jonathandarwin.currency.databinding.MainActivityBinding
import com.jonathandarwin.currency.util.ThemeUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theme = ThemeUtil.getTheme(this)
        if(theme.equals(ThemeUtil.DARK_THEME)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.Theme_CurrencyDark)
        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            setTheme(R.style.Theme_Currency)
        }
        DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
    }
}