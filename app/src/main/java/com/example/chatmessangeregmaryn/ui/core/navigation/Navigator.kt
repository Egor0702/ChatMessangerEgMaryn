package com.example.chatmessangeregmaryn.ui.core.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.domain.friends.FriendEntity
import com.example.chatmessangeregmaryn.presentation.Authenticator
import com.example.chatmessangeregmaryn.presentation.viewmodel.MediaViewModel
import com.example.chatmessangeregmaryn.remote.service.ApiService
import com.example.chatmessangeregmaryn.ui.account.AccountActivity
import com.example.chatmessangeregmaryn.ui.core.PermissionManager
import com.example.chatmessangeregmaryn.ui.register.RegisterActivity
import com.example.chatmessangeregmaryn.ui.home.HomeActivity
import com.example.chatmessangeregmaryn.ui.login.LoginActivity
import com.example.chatmessangeregmaryn.ui.user.UserActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator,
                    private val permissionManager: PermissionManager
) {
init {
    Log.d("Egor", "Всем хло, мы в Navigator")
}
    fun showMain(context: Context) {
        Log.d("Egor", "Всем хло, мы в showMain()")
        when (authenticator.userLoggedIn()) {
            true -> showHome(context, false)
            false -> showLogin(context, false)
        }
    }

    fun showLogin(context: Context, newTask: Boolean = true) = context.startActivity<LoginActivity>(newTask = newTask)

    fun showHome(context: Context, newTask: Boolean = true) = context.startActivity<HomeActivity>(newTask = newTask)

    fun showSignUp(context: Context) = context.startActivity<RegisterActivity>()

    fun showAccount(context: Context) {
        Log.d("Egor", "Navigator showAccount()")
        context.startActivity<AccountActivity>()
    }

    fun showUser(context: Context, friendEntity: FriendEntity) { // передаем в UserActivity и используем в UserFragment данные через Bundle
        Log.d("Egor", "Navigator showUser()")
        val bundle = Bundle()
        bundle.putString(ApiService.PARAM_IMAGE, friendEntity.image)
        bundle.putString(ApiService.PARAM_NAME, friendEntity.name)
        bundle.putString(ApiService.PARAM_EMAIL, friendEntity.email)
        bundle.putString(ApiService.PARAM_STATUS, friendEntity.status)
        context.startActivity<UserActivity>(args = bundle) // запуcкаем UserActivity
    }

    fun showPickFromDialog(activity: AppCompatActivity, onPick: (fromCamera: Boolean) -> Unit) { // показывает диалог
        Log.d("Egor", "Navigator showPickFromDialog")
        val options = arrayOf( // массив с названием объекта на который будет запрашиваться разрешение
                activity.getString(R.string.camera),
                activity.getString(R.string.gallery)
        )
        Log.d("Egor", "Navigator showPickFromDialog after options")
        val builder = AlertDialog.Builder(activity) // созздаем объект диалога
        Log.d("Egor", "Navigator showPickFromDialog after builder")
        builder.setTitle(activity.getString(R.string.pick)) // утснавливаем название
        builder.setItems(options) { _, item -> // передаем в него массив
            when (options[item]) { // разбираем массив по элементам и вызываем соответсвующие методы
                activity.getString(R.string.camera) -> {
                    permissionManager.checkCameraPermission(activity) {
                        onPick(true)
                    }
                }
                activity.getString(R.string.gallery) -> {
                    permissionManager.checkWritePermission(activity) {
                        onPick(false)
                    }
                }
            }
        }
            builder.show()

    }

    fun showCamera(activity: AppCompatActivity, uri: Uri) {
        Log.d("Egor", "Navigator showCamera()")
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        activity.startActivityForResult(intent, MediaViewModel.CAPTURE_IMAGE_REQUEST_CODE)
    }

    fun showGallery(activity: AppCompatActivity) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"

        activity.startActivityForResult(intent, MediaViewModel.PICK_IMAGE_REQUEST_CODE)
    }

    fun showEmailNotFoundDialog(context: Context, email: String) { // метод, который вызывае алердиалог, если пользователь не найден
        Log.d("Egor", "HomeActivity showEmailNotFoundDialog()")
        AlertDialog.Builder(context) // создаем объект диалога
                .setMessage(context.getString(R.string.message_promt_app)) // устанавливаем сообщение, которое будет отображаться в диалоге

                .setPositiveButton("Да") { _, _ -> // устанавливаем слушателя на кнопку "Да"
                    showEmailInvite(context, email) // будет открываться приложение с почтой
                }

                .setNegativeButton("Нет") {dialog, _ -> dialog.cancel() } // в случае, если пользователь нажал "Нет", закрываем диалог
                .setIcon(android.R.drawable.ic_dialog_alert) // устанавливаем иконку, которая идет в API 30
                .show() // обязательно указываем этот метод, иначе диалог не будет отображен
    }

    fun showEmailInvite(context: Context, email: String) { // формирует и запускает intent для перехода в приложение почты. Принимает Context, String: email. Ничего не возвращает.
        Log.d("Egor", "Navigator showEmailInvite()")
        val appPackageName = context.packageName
        val emailIntent = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null
            )
        )
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.resources.getString(R.string.message_subject_promt_app))
        emailIntent.putExtra(
            Intent.EXTRA_TEXT, context.resources.getString(R.string.message_text_promt_app) + " "
                    + context.resources.getString(R.string.url_google_play) + appPackageName
        )
        context.startActivity(Intent.createChooser(emailIntent, "Отправить"))
    }
}


private inline fun <reified T> Context.startActivity(newTask: Boolean = false, args: Bundle? = null) {
    Log.d("Egor", "Всем хло, мы в startActivity() class Navigator")
    this.startActivity(Intent(this, T::class.java).apply {
        if (newTask) { // если RegisterActivity - то таск не очищается и мы сможеи вернуться на страничку в входом в аккаунт
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // с помощью этого флага активити находит свой таск и открывает запущенное активити
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // таск будет очищен, а вызываемое Activity станет в нем корневым.
        }
        putExtra("args", args)
    })
}

