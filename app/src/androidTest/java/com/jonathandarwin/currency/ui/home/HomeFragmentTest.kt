package com.jonathandarwin.currency.ui.home

import androidx.fragment.app.testing.launchFragmentInContainer
import com.jonathandarwin.currency.di.CoroutineModule
import com.jonathandarwin.currency.di.DatabaseModule
import com.jonathandarwin.currency.di.NetworkModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test

/**
 * Created By : Jonathan Darwin on June 02, 2021
 */
//@HiltAndroidTest
//@UninstallModules(DatabaseModule::class, NetworkModule::class, CoroutineModule::class)
//class HomeFragmentTest {
//
//    @get:Rule
//    val hiltRule = HiltAndroidRule(this)
//
//    @Test
//    fun testLaunchFragment() {
//        val scenario = launchFragmentInContainer<HomeFragment>()
//    }
//}