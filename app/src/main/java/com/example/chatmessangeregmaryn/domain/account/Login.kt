package com.example.chatmessangeregmaryn.domain.account

import android.util.Log
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import javax.inject.Inject

class Login @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<AccountEntity, Login.Params>() {
    init {
        Log.d("Egor", "Всем хло, мы в Login")
    }
    override suspend fun run(params: Params): Either<Failure, AccountEntity> {
        Log.d("Egor", "Login run()")
        return accountRepository.login(params.email, params.password)
    }
    data class Params(val email: String, val password: String)
}