package com.example.chatmessangeregmaryn.presentation

import com.example.chatmessangeregmaryn.cache.SharedPrefsManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator
@Inject constructor(
    val sharedPrefsManager: SharedPrefsManager
){
    fun userLoggedIn() = sharedPrefsManager.containsAnyAccount()
}