package com.example.chatmessangeregmaryn.presentation

import android.util.Log
import com.example.chatmessangeregmaryn.cache.SharedPrefsManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator
@Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager
){
    init {
        Log.d("Egor", "Всем хло, мы в Authenticator")
    }
    fun userLoggedIn(): Boolean = sharedPrefsManager.containsAnyAccount()
}