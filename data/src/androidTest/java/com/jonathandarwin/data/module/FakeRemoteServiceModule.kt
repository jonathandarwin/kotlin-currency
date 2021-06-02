package com.jonathandarwin.data.module

import com.jonathandarwin.data.network.FakeRemoteService
import com.jonathandarwin.data.network.RemoteService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Created By : Jonathan Darwin on June 02, 2021
 */
@Module
@InstallIn(ApplicationComponent::class)
abstract class FakeRemoteServiceModule {
    @Binds
    abstract fun bindProvideFakeRemoteService(fakeRemoteService: FakeRemoteService): RemoteService
}