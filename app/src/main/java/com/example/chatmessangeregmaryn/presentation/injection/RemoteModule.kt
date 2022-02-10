package com.example.chatmessangeregmaryn.presentation.injection

import android.util.Log
import dagger.Module
import dagger.Provides
import com.example.chatmessangeregmaryn.BuildConfig
import com.example.chatmessangeregmaryn.data.account.AccountRemote
import com.example.chatmessangeregmaryn.data.friends.FriendsRemote
import com.example.chatmessangeregmaryn.remote.account.AccountRemoteImpl
import com.example.chatmessangeregmaryn.remote.core.Request
import com.example.chatmessangeregmaryn.remote.friends.FriendsRemoteImpl
import com.example.chatmessangeregmaryn.remote.service.ApiService
import com.example.chatmessangeregmaryn.remote.service.ServiceFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        Log.d("Egor", "RemotModule provideApiService()")
        return ServiceFactory.makeService(BuildConfig.DEBUG)
    }

    @Singleton
    @Provides
    fun provideAccountRemote(request: Request, apiService: ApiService): AccountRemote {
        Log.d("Egor", "RemotModule provideAccountRemote()")
        return AccountRemoteImpl(request, apiService)
    }
    @Singleton
    @Provides
    fun provideFriendsRemote(request: Request, apiService: ApiService): FriendsRemote {
        Log.d("Egor", "RemotModule provideFriendsRemote()")
        return FriendsRemoteImpl(request, apiService)
    }

}