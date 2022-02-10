package com.example.chatmessangeregmaryn.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chatmessangeregmaryn.domain.account.*
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    val registerUseCase:Register,
    val loginUseCase: Login,
    val getAccountUseCase: GetAccount,
    val logoutUseCase: Logout,
    val editAccountUseCase: EditAccount) : BaseViewModel() {
    init {
        Log.d("Egor", "Всем хло, мы в AccountViewModel")
    }
    var registerData: MutableLiveData<None> = MutableLiveData()
    var accountData: MutableLiveData<AccountEntity> = MutableLiveData()
    var logoutData: MutableLiveData<None> = MutableLiveData()
    var editAccountData: MutableLiveData<AccountEntity> = MutableLiveData()

    fun register(email: String, name: String, password: String) {
        Log.d("Egor", "AccountViewModel register()")
        registerUseCase(Register.Params(email, name, password)) {
            it.either(::handleFailure, ::handleRegister)
        }
    }

    fun login(email: String, password: String) {
        Log.d("Egor", "AccountViewModel login()")
        loginUseCase(Login.Params(email, password)) {
            it.either(::handleFailure, ::handleAccount)
        }
    }

    fun getAccount() {
        Log.d("Egor", "AccountViewModel getAccount()")
        getAccountUseCase(None()) { it.either(::handleFailure, ::handleAccount) }
    }

    fun logout() {
        Log.d("Egor", "AccountViewModel logout()")
        logoutUseCase(None()) { it.either(::handleFailure, ::handleLogout) }
    }

    fun editAccount(entity: AccountEntity) {
        editAccountUseCase(entity) { it.either(::handleFailure, ::handleEditAccount) }
    }

    private fun handleRegister(none: None) {
        Log.d("Egor", "AccountViewModel handleReister()")
        this.registerData.value = none
    }

    private fun handleAccount(account: AccountEntity) {
        Log.d("Egor", "AccountViewModel handleAccount()")
        this.accountData.value = account
    }

    private fun handleLogout(none: None) {
        Log.d("Egor", "AccountViewModel handleLogout()")
        this.logoutData.value = none
    }

    private fun handleEditAccount(account: AccountEntity) {
        this.editAccountData.value = account
    }

    override fun onCleared() {
        Log.d("Egor", "AccountViewModel onCleared()")
        super.onCleared()
        registerUseCase.unsubscribe()
        loginUseCase.unsubscribe()
        getAccountUseCase.unsubscribe()
        logoutUseCase.unsubscribe()
    }
}