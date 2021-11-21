package com.example.chatmessangeregmaryn.remote.core

import android.util.Log
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Request @Inject constructor(private val networkHandler: NetworkHandler) {

    fun <T : BaseResponse, R> make(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
        Log.d("Egor", "Is network: ${networkHandler.isConnected}")
        return when (networkHandler.isConnected) {
            true -> execute(call, transform)
            false, null -> Either.Left(Failure.NetworkConnectionError)
        }
    }

    private fun <T : BaseResponse, R> execute(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
        Log.d("Egor", "Мы в execute")
        return try {
            Log.d("Egor", "Мы в execute1")
            val response = call.execute()
            Log.d("Egor", "response: ${response.isSucceed()}")
            when (response.isSucceed()) {
                true -> Either.Right(transform((response.body()!!)))
                false -> Either.Left(Failure.ServerError)
            }
        } catch (exception: Throwable) {
            Log.d("Egor", "${exception.message}")
            Either.Left(Failure.ServerError)
        }
    }

    fun <T : BaseResponse> Response<T>.isSucceed(): Boolean {
        return isSuccessful && body() != null && (body() as BaseResponse).success == 1
    }

    fun <T : BaseResponse> Response<T>.parseError(): Failure { // функция расширения для Response
        val message = (body() as BaseResponse).message
        return when (message) {
            "email already exists" -> Failure.EmailAlreadyExistError
            "error in email or password" -> Failure.AuthError
            "Token is invalid" -> Failure.TokenError
            else -> Failure.ServerError
        }
    }
}