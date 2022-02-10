package com.example.chatmessangeregmaryn.domain.media

import android.net.Uri
import android.util.Log
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class CreateImageFile @Inject constructor( // класс для создания файла изображения.
    private val mediaRepository: MediaRepository
) : UseCase<Uri, None>() {
    init {
        Log.d("Egor", "Всем хло, мы в CreateImageFile")
    }

    override suspend fun run(params: None): Either<Failure, Uri> {
        Log.d("Egor", "CreateImageFile run()")
        return mediaRepository.createImageFile()
    }
}