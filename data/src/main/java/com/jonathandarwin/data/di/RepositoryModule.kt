package com.jonathandarwin.data.di

import com.jonathandarwin.data.repository.CurrencyRepositoryImpl
import com.jonathandarwin.domain.abstraction.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindCurrencyRepository(currencyRepository: CurrencyRepositoryImpl): CurrencyRepository
}