package com.example.chatmessangeregmaryn.presentation.injection

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import com.example.chatmessangeregmaryn.data.account.AccountCache
import com.example.chatmessangeregmaryn.data.account.AccountRemote
import com.example.chatmessangeregmaryn.data.account.AccountRepositoryImpl
import com.example.chatmessangeregmaryn.data.friends.FriendsRemote
import com.example.chatmessangeregmaryn.data.friends.FriendsRepositoryImpl
import com.example.chatmessangeregmaryn.data.media.MediaRepositoryImpl
import com.example.chatmessangeregmaryn.domain.account.AccountRepository
import com.example.chatmessangeregmaryn.domain.friends.FriendsRepository
import com.example.chatmessangeregmaryn.domain.media.MediaRepository
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    init {
        Log.d("Egor", "Всем хло, мы в AppModule")
    }

    @Provides
    @Singleton
    fun provideAppContext() = context

    @Provides
    @Singleton
    fun provideAccountRepository(remote: AccountRemote, cache: AccountCache): AccountRepository {
        Log.d("Egor", "AppModule provideAccountRepository()")
        return AccountRepositoryImpl(remote, cache)
    }

    @Provides
    @Singleton
    fun provideFriendsRepository(remote: FriendsRemote, accountCache: AccountCache): FriendsRepository {
        Log.d("Egor", "AppModule provideFriendsRepository()")
        return FriendsRepositoryImpl(accountCache, remote)
    }

    @Provides
    @Singleton
    fun provideMediaRepository(context: Context): MediaRepository {
        return MediaRepositoryImpl(context)
    }
}