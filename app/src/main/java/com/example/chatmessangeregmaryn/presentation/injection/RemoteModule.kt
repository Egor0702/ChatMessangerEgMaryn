package com.example.chatmessangeregmaryn.presentation.injection

import dagger.Module
import dagger.Provides
import com.example.chatmessangeregmaryn.BuildConfig
import com.example.chatmessangeregmaryn.data.account.AccountRemote
import com.example.chatmessangeregmaryn.remote.account.AccountRemoteImpl
import com.example.chatmessangeregmaryn.remote.core.Request
import com.example.chatmessangeregmaryn.remote.service.ApiService
import com.example.chatmessangeregmaryn.remote.service.ServiceFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService = ServiceFactory.makeService(BuildConfig.DEBUG)

    @Singleton
    @Provides
    fun provideAccountRemote(request: Request, apiService: ApiService): AccountRemote =
            AccountRemoteImpl(request, apiService)

}