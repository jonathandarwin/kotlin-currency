package com.jonathandarwin.data.di

import com.jonathandarwin.data.database.FakeCurrencyDao
import com.jonathandarwin.data.database.dao.CurrencyDAO
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

/**
 * Created By : Jonathan Darwin on June 02, 2021
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
abstract class FakeDatabaseModule {
    @Binds
    abstract fun bindFakeCurrencyDao(fakeCurrencyDao: FakeCurrencyDao): CurrencyDAO
}