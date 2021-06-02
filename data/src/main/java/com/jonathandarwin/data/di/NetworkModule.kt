package com.jonathandarwin.data.di

import com.jonathandarwin.data.network.RemoteService
import com.jonathandarwin.data.network.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideRemoteService(): RemoteService = RetrofitBuilder.remoteService
}