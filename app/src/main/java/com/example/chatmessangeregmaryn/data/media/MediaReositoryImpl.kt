package com.example.chatmessangeregmaryn.data.media

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.chatmessangeregmaryn.domain.media.MediaRepository
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import java.io.File
import javax.inject.Singleton

@Singleton
class MediaRepositoryImpl(val context: Context) : MediaRepository {
    init {
        Log.d(log, "Всем хло, мы в MediaRepositoryImpl")
    }
    companion object{
        const val log = "Egor"
    }

    override fun createImageFile(): Either<Failure, Uri> { // создаем файл изображения
        val uri = MediaHelper.createImageFile(context) // делегируем создание файла изображения классу MediaHelper.
        return if (uri == null) { // проверка на null
            Either.Left(Failure.FilePickError)
        } else {
            Either.Right(uri)
        }
    }

    override fun encodeImageBitmap(bitmap: Bitmap?): Either<Failure, String> { //  метод, ответсвенный за кодирование изображения в строку
        if (bitmap == null) return Either.Left(Failure.FilePickError) // проверяем Bitmap-изображение на null.

        val imageString = MediaHelper.encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100) // делегируем кодирование изображения в строку MediaHelper.

        return if (imageString.isEmpty()) {
            Either.Left(Failure.FilePickError)
        } else {
            Either.Right(imageString)
        }
    }

    override fun getPickedImage(uri: Uri?): Either<Failure, Bitmap> { // сохраняем файл
        if (uri == null) return Either.Left(Failure.FilePickError) // проверяем Uri-путь на null

        val filePath = MediaHelper.getPath(context, uri) // делегируем получение пути на файл
        val image = MediaHelper.saveBitmapToFile(File(filePath)) // делегируем сохранение изображения

        return if (image == null) { // проверяем на null
            Either.Left(Failure.FilePickError)
        } else {
            Either.Right(image)
        }
    }
}