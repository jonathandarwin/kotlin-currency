package com.jonathandarwin.data.di

import android.content.Context
import com.jonathandarwin.data.database.AppDatabase
import com.jonathandarwin.data.database.dao.CurrencyDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created By : Jonathan Darwin on June 02, 2021
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideCurrencyDao(@ApplicationContext context: Context): CurrencyDAO = AppDatabase.getInstance(context).currencyDAO
}