package com.example.chatmessangeregmaryn.presentation.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import com.example.chatmessangeregmaryn.data.account.AccountCache
import com.example.chatmessangeregmaryn.data.account.AccountRemote
import com.example.chatmessangeregmaryn.data.account.AccountRepositoryImpl
import com.example.chatmessangeregmaryn.data.friends.FriendsRemote
import com.example.chatmessangeregmaryn.data.friends.FriendsRepositoryImpl
import com.example.chatmessangeregmaryn.domain.account.AccountRepository
import com.example.chatmessangeregmaryn.domain.friends.FriendsRepository
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppContext() = context

    @Provides
    @Singleton
    fun provideAccountRepository(remote: AccountRemote, cache: AccountCache): AccountRepository {
        return AccountRepositoryImpl(remote, cache)
    }

    @Provides
    @Singleton
    fun provideFriendsRepository(remote: FriendsRemote, accountCache: AccountCache): FriendsRepository {
        return FriendsRepositoryImpl(accountCache, remote)
    }
}