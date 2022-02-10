package com.example.chatmessangeregmaryn.data.media

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.MediaStore.Images.Thumbnails.DATA
import android.util.Base64
import android.util.Log
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

object MediaHelper {
    init {
        Log.d("Egor", "Всем хло, мы в MediaHelper")
    }

    fun encodeToBase64(bitmap: Bitmap, compressFormat: Bitmap.CompressFormat, quality: Int): String { // метод для кодирования данных при помощи утилиты Base64
        Log.d("Egor", "MediaHelper encodeToBase64()")
        val byteArrayOS = ByteArrayOutputStream() // создаем массив байтов в качестве места вывода
        bitmap.compress(compressFormat, quality, byteArrayOS) // сжимаем картинку. Первый аргумент - формат (можно - JPEG, PNG, WEBP). Во втором аргументе указываем степень сжатия от 0 до 100, 0 - для получения малого размера файла, 100 - максимальное качество (Формат PNG не поддерживает сжатие с потерей качества и будет игнорировать данное значение.). В третьем аргуменете передаем файловый поток
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT) // кодируем сжатую картинку в строку. 2ой аргумент соответсвует тому, что результат кодирования у нас будет RFC 2045
    }

    fun createImageFile(context: Context): Uri? { // создаем файл изображения
        Log.d("Egor", "MediaHelper createImageFile()")
        val file = File(Environment.getExternalStorageDirectory().toString() + "/DCIM/", "IMG_" + Date().time + ".png") // создаем объект файла. Параметр принмиает путь
        Log.d("Egor", "MediaHelper file: $file")
        val imgUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context, context.packageName + ".provider", file) // получаем ури для нашего файла, а качестве полномчия идет имя пакета
        } else {
            Uri.fromFile(file) // для API 24 и ниже используем следующий код
        }
        return imgUri // возвращаем ури с путем к файлу
    }

    fun getPath(context: Context, uri: Uri): String? { // возвращает путь к файлу в формате string
        Log.d("Egor", "MediaHelper getPath()")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val mInputPFD = context.contentResolver.openFileDescriptor(uri, "r") ?: return null // контентресольвер дает возможность взаимодействия с контент провадером, поэтому получаем его экземпляр. "r" означает, что файл может быть открыт только для чтения. Данный запрос возвращает нам ParcelableFileDiscriptor
            val fileDescriptor = mInputPFD.fileDescriptor // преобразуем ParcelableFileDiscriptor в FileDiscriptor

            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor) // получаем объект Bimap по дескриптору
            val tempUri = getImageUri(context, image) // передаем логику выполнения в другой метод, который возвращает ури

            return getAbsolutePath(context, tempUri) // возвращает полный путь к файлу

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]

                    if ("primary".equals(type, ignoreCase = true)) {

                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }

                } else if (isDownloadsDocument(uri)) {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                    )
                    return getAbsolutePath(context, contentUri)

                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                    val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])

                    return getAbsolutePath(
                            context,
                            contentUri,
                            selection,
                            selectionArgs
                    )
                }
            } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {
                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getAbsolutePath(
                        context,
                        uri
                )

            } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
                return uri.path
            }
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        Log.d("Egor", "MediaHelper isExternalStorageDocument()")
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        Log.d("Egor", "MediaHelper isDownloadsDocument()")
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        Log.d("Egor", "MediaHelper isMediaDocument()")
        return "com.android.providers.media.documents" == uri.authority
    }

    private fun isGooglePhotosUri(uri: Uri): Boolean {
        Log.d("Egor", "MediaHelper isGooglePhotosUri()")
        return "com.google.android.apps.photos.content" == uri.authority
    }


    fun saveBitmapToFile(file: File): Bitmap? {
        Log.d("Egor", "MediaHelper saveBitmapToFile()")
        // BitmapFactory options to downsize the image
        val o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        o.inSampleSize = 6

        var inputStream = FileInputStream(file)
        BitmapFactory.decodeStream(inputStream, null, o)
        inputStream.close()

        // Новый размер, который мы ходим масштабировать до:
        val requiredSize = 75

        // Find the correct scale value. It should be the power of 2.
        var scale = 1
        while (o.outWidth / scale / 2 >= requiredSize && o.outHeight / scale / 2 >= requiredSize) {
            scale *= 2
        }

        val o2 = BitmapFactory.Options()
        o2.inSampleSize = scale
        inputStream = FileInputStream(file)

        val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
        inputStream.close()

        // Overriding the original image file
        file.createNewFile()
        val outputStream = FileOutputStream(file)

        if (selectedBitmap == null) {
            return null
        }

        selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

        return selectedBitmap
    }



    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        Log.d("Egor", "MediaHelper getImageUri()")
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            val contentValues = ContentValues().apply {
//                put(MediaStore.MediaColumns.DISPLAY_NAME, "Title")
//                put(MediaStore.MediaColumns.MIME_TYPE, "image/JPEG")
//                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
//                put(MediaStore.MediaColumns.IS_PENDING, 1)
//            }
//            return inContext.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
        //} else {
            val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
            return Uri.parse(path)
        }



    private fun getAbsolutePath(context: Context, uri: Uri?, selection: String? = null, selectionArgs: Array<String>? = null): String? { // возвращаем полный путь к файлу
        Log.d("Egor", "MediaHelper getAbsolutePath()")
        if (uri == null) { // проверяем на null
            return null
        }
        var path: String? = null // объявляем переменную, которая будет хранить путь

        val projection = arrayOf(MediaStore.Images.Media.DATA)
        //val projection = arrayOf(MediaStore.Images.Media._ID) // объявляем массив, который будет хранить идентификаторы файла
        val cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null) // создаем объект курсор, которым мы пробежимся по списку
        if (cursor != null && cursor.moveToFirst()) { // если не null и он возвращает первый элемент:
            val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA) // возвращает индекс "0"
            path = cursor.getString(columnIndex)
            Log.d("Egor", "path: $path")
        }
        cursor?.close()
        return path
    }
}