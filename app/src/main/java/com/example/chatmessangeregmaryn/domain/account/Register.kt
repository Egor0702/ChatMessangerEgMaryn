package com.example.chatmessangeregmaryn.domain.account

import android.util.Log
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import javax.inject.Inject

class Register @Inject constructor(private val repository: AccountRepository) : UseCase<None, Register.Params>() {
    init {
        Log.d("Egor", "Всем хло, мы в Register")
    }

    override suspend fun run(params: Params): Either<Failure, None> {
        Log.d("Egor", "Register run()")
        return repository.register(params.email, params.name, params.password)
    }

    data class Params(val email: String, val name: String, val password: String)
}

