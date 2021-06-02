package com.jonathandarwin.data.module

import com.jonathandarwin.data.database.FakeCurrencyDao
import com.jonathandarwin.data.database.dao.CurrencyDAO
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Created By : Jonathan Darwin on June 02, 2021
 */
@Module
@InstallIn(ApplicationComponent::class)
abstract class FakeDatabaseModule {
    @Binds
    abstract fun bindFakeCurrencyDao(fakeCurrencyDao: FakeCurrencyDao): CurrencyDAO
}