package com.example.chatmessangeregmaryn.presentation.viewmodel

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chatmessangeregmaryn.domain.media.CreateImageFile
import com.example.chatmessangeregmaryn.domain.media.EncodeImageBitmap
import com.example.chatmessangeregmaryn.domain.media.GetPickedImage
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class MediaViewModel @Inject constructor(
    val createImageFileUseCase: CreateImageFile,
    val encodeImageBitmapUseCase: EncodeImageBitmap,
    val getPickedImageUseCase: GetPickedImage
) : BaseViewModel() {
    init {
        Log.d(log, "сем хло, мы в MediaViewModel")
    }

    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 10001
        const val CAPTURE_IMAGE_REQUEST_CODE = 10002
        const val log = "Egor"
    }

    class PickedImage(val bitmap: Bitmap, val string: String)

    var cameraFileCreatedData: MutableLiveData<Uri> = MutableLiveData()
    private var imageBitmapData: MutableLiveData<Bitmap> = MutableLiveData()
    var pickedImageData: MutableLiveData<PickedImage> = MutableLiveData()

    fun createCameraFile() {
        Log.d(log," MediaViewModel createCameraFile()")
        createImageFileUseCase(None()) { it.either(::handleFailure, ::handleCameraFileCreated) }
    }

    private fun getPickedImage(uri: Uri?) {
        Log.d(log," MediaViewModel getPickedImage()")
        updateProgress(true)
        getPickedImageUseCase(uri) { it.either(::handleFailure, ::handleImageBitmap) }
    }

    private fun encodeImage(bitmap: Bitmap) {
        Log.d(log," MediaViewModel encodeImage()")
        encodeImageBitmapUseCase(bitmap) { it.either(::handleFailure, ::handleImageString) }
    }


    fun onPickImageResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(log," MediaViewModel onPickImageResult()")
        if (resultCode == Activity.RESULT_OK) {
            val uri = when (requestCode) {
                PICK_IMAGE_REQUEST_CODE -> data?.data
                CAPTURE_IMAGE_REQUEST_CODE -> cameraFileCreatedData.value
                else -> null
            }

            getPickedImage(uri)
        }
    }

    private fun handleCameraFileCreated(uri: Uri) {
        Log.d(log," MediaViewModel handleCameraFileCreated()")
        this.cameraFileCreatedData.value = uri
    }

    private fun handleImageBitmap(bitmap: Bitmap) {
        Log.d(log," MediaViewModel handleImageBitmap()")
        this.imageBitmapData.value = bitmap

        encodeImage(bitmap)
    }

    private fun handleImageString(string: String) {
        Log.d(log," MediaViewModel handleImageString()")
        this.pickedImageData.value = PickedImage(imageBitmapData.value!!, string)
        updateProgress(false)
    }

    override fun onCleared() {
        Log.d(log," MediaViewModel onCleared()")
        super.onCleared()
        createImageFileUseCase.unsubscribe()
        encodeImageBitmapUseCase.unsubscribe()
        getPickedImageUseCase.unsubscribe()
    }
}