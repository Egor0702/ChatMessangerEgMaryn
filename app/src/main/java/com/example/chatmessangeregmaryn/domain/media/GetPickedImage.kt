package com.example.chatmessangeregmaryn.domain.media

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import javax.inject.Inject

class GetPickedImage @Inject constructor( // класс для получения изображения Bitmap.
    private val mediaRepository: MediaRepository
) : UseCase<Bitmap, Uri?>() {
    init {
        Log.d("Egor", "Всем хло, мы в EncodeImageBitmap")
    }

    override suspend fun run(params: Uri?): Either<Failure, Bitmap> {
        Log.d("Egor", "GetPickedImage run()")
        return mediaRepository.getPickedImage(params)
    }
}