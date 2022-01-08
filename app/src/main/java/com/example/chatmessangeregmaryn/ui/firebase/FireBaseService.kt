package com.example.chatmessangeregmaryn.ui.service

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.chatmessangeregmaryn.domain.account.UpdateToken
import com.example.chatmessangeregmaryn.ui.App
import com.example.chatmessangeregmaryn.ui.firebase.NotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

class FirebaseService : FirebaseMessagingService() {

    @Inject
    lateinit var updateToken: UpdateToken

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        App.appComponent.inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Handler(Looper.getMainLooper()).post { // делегирование обработки и отображения нотификации объекту NotificationHelper. При помощи Handler(MainLooper).post выражение запущено в UI потоке.
            notificationHelper.sendNotification(remoteMessage)
        }

        override fun onNewToken(token: String?) {
            Log.e("fb token", ": $token")
            if (token != null) {
                updateToken(UpdateToken.Params(token))
            }
        }
    }
}