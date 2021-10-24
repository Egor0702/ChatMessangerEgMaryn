package com.example.chatmessangeregmaryn.domain.type

sealed class Failure {
    object NetworkConnectionError : Failure() // ошибка сети
    object ServerError : Failure() // ошибка при обращении на сервер
    object AuthError : Failure() // ошибка авторизации(неправильный email/пароль).
    object TokenError : Failure() // ошибка о невалидном токене
    object EmailAlreadyExistError : Failure() // ошибка при регистрации
    object NoSavedAccountsError : Failure() //ошибка получения текущего аккаунта, при его отсутствии
}