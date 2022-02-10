package com.example.chatmessangeregmaryn.domain.media

import android.graphics.Bitmap
import android.util.Log
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import javax.inject.Inject

class EncodeImageBitmap @Inject constructor( // класс для кодирования изображения в строку.
    private val mediaRepository: MediaRepository
) : UseCase<String, Bitmap?>() {
    init {
        Log.d("Egor", "Всем хло, мы в EncodeImageBitmap")
    }


    override suspend fun run(params: Bitmap?): Either<Failure, String> {
        Log.d("Egor", "EncodeImageBitmap run()")
        return mediaRepository.encodeImageBitmap(params)
    }
}