package com.example.chatmessangeregmaryn.presentation.injection

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import com.example.chatmessangeregmaryn.cache.AccountCacheImpl
import com.example.chatmessangeregmaryn.cache.SharedPrefsManager
import com.example.chatmessangeregmaryn.data.account.AccountCache
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences { // контекст уже представлен в appModule
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideAccountCache(prefsManager: SharedPrefsManager): AccountCache = AccountCacheImpl(prefsManager)
}