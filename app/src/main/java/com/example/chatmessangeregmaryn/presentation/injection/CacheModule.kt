package com.example.chatmessangeregmaryn.presentation.injection

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import dagger.Module
import dagger.Provides
import com.example.chatmessangeregmaryn.cache.AccountCacheImpl
import com.example.chatmessangeregmaryn.cache.SharedPrefsManager
import com.example.chatmessangeregmaryn.data.account.AccountCache
import com.example.chatmessangeregmaryn.presentation.Authenticator
import javax.inject.Singleton

@Module
class CacheModule {
    init {
        Log.d("Egor", "Всем хло, мы в CacheModule")
    }
    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences { // контекст уже представлен в appModule
        Log.d("Egor", "CacheModule fun provideSharedPreferences(context: Context)")
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideAccountCache(prefsManager: SharedPrefsManager): AccountCache = AccountCacheImpl(prefsManager)

}