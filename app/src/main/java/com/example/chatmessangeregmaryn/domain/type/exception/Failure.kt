package com.example.chatmessangeregmaryn.domain.type.exception

sealed class Failure {
    object NetworkConnectionError : Failure()
    object ServerError : Failure()
}