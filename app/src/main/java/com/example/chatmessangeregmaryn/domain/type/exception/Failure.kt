package com.example.chatmessangeregmaryn.domain.type.exception

sealed class Failure {
    object NetworkConnectionError : Failure() // ошибка сети
    object ServerError : Failure() // ошибка при обращении на сервер
    object EmailAlreadyExistError : Failure() // ошибка при регистрации
}