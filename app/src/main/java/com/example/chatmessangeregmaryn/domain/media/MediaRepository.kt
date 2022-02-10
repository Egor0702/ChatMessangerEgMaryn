package com.example.chatmessangeregmaryn.domain.media

import android.graphics.Bitmap
import android.net.Uri
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure

interface MediaRepository { // объявляет методы для работы с файлами
    fun createImageFile(): Either<Failure, Uri> // создает файл изображения. Используется для передачи в Intent камеры при выборе изображения.
    fun encodeImageBitmap(bitmap: Bitmap?): Either<Failure, String> //  кодирует изображение в строку. Используется для отправки изображения на сервер.
    fun getPickedImage(uri: Uri?): Either<Failure, Bitmap> // возвращает изображение в формате Bitmap используя путь Uri .
}