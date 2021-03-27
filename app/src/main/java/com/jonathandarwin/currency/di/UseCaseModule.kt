package com.jonathandarwin.currency.di

import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import com.jonathandarwin.domain.usecase.CurrencyUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindCurrencyUseCase(currencyUseCaseImpl: CurrencyUseCaseImpl): CurrencyUseCase
}