package com.jonathandarwin.data.di

import com.jonathandarwin.data.network.FakeRemoteService
import com.jonathandarwin.data.network.RemoteService
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
    replaces = [NetworkModule::class]
)
abstract class FakeRemoteServiceModule {
    @Binds
    abstract fun bindProvideFakeRemoteService(fakeRemoteService: FakeRemoteService): RemoteService
}