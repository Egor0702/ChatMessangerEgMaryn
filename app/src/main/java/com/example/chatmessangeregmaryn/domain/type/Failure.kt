package com.example.chatmessangeregmaryn.domain.type

sealed class Failure {
    object NetworkConnectionError : Failure() // ошибка сети
    object ServerError : Failure() // ошибка при обращении на сервер
    object AuthError : Failure() // ошибка авторизации(неправильный email/пароль).
    object TokenError : Failure() // ошибка о невалидном токене
    object EmailAlreadyExistError : Failure() // ошибка при регистрации
    object AlreadyFriendError : Failure() // ошибка при попытке добавить в друзья ползователя,, который уже является другом
    object AlreadyRequestedFriendError : Failure() // ошибка повторного запроса на добавление в друзья
    object ContactNotFoundError : Failure() // ошибка при добавлении в друзья несуществующего пользователя
    object NoSavedAccountsError : Failure() //ошибка получения текущего аккаунта, при его отсутствии
    object FilePickError : Failure() //  объект для обозначения ошибки выбора файла.
}