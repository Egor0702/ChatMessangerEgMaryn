package com.example.chatmessangeregmaryn.ui.core.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.presentation.Authenticator
import com.example.chatmessangeregmaryn.ui.register.RegisterActivity
import com.example.chatmessangeregmaryn.ui.home.HomeActivity
import com.example.chatmessangeregmaryn.ui.login.LoginActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {
init {
    Log.d("Egor", "Всем хло, мы в Navigator")
}
    fun showMain(context: Context) {
        when (authenticator.userLoggedIn()) {
            true -> showHome(context, false)
            false -> showLogin(context, false)
        }
    }

    fun showLogin(context: Context, newTask: Boolean = true) = context.startActivity<LoginActivity>(newTask = newTask)

    fun showHome(context: Context, newTask: Boolean = true) = context.startActivity<HomeActivity>(newTask = newTask)

    fun showSignUp(context: Context) = context.startActivity<RegisterActivity>()

    fun showEmailInvite(context: Context, email: String) { // формирует и запускает intent для перехода в приложение почты. Принимает Context, String: email. Ничего не возвращает.
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
    Log.d("Egor", "Всем хло, мы в startActivity")
    this.startActivity(Intent(this, T::class.java).apply {
        if (newTask) {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        putExtra("args", args)
    })
}
