package com.example.chatmessangeregmaryn.domain.account

import android.util.Log
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import javax.inject.Inject

class UpdateToken @Inject constructor(private val accountRepository: AccountRepository) : UseCase<None, UpdateToken.Params>() {
    init {
        Log.d("Egor", "Всем хло, мы в UpdateToken")
    }

    override suspend fun run(params: Params): Either<Failure, None> {
        Log.d("Egor", "UpdateToken run()")
        return accountRepository.updateAccountToken(params.token)
    }
    data class Params(val token: String)
}