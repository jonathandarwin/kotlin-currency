package com.jonathandarwin.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created By : Jonathan Darwin on June 02, 2021
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoroutineModule::class]
)
object FakeDispatcherModule {
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}