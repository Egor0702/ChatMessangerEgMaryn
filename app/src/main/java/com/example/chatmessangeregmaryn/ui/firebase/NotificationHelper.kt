package com.example.chatmessangeregmaryn.ui.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.domain.friends.FriendEntity
import com.example.chatmessangeregmaryn.remote.service.ApiService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import javax.inject.Inject

class NotificationHelper @Inject constructor(val context: Context) { // для парса firebase-сообщений и отображения нотификаций
    init {
        Log.d("Egor", "Всем хло, мы в NotificationHelper")
        createChannels()
    }
    companion object {
        const val MESSAGE = "message"
        const val JSON_MESSAGE = "firebase_json_message"
        const val TYPE = "type"
        const val TYPE_ADD_FRIEND = "addFriend"
        const val TYPE_APPROVED_FRIEND = "approveFriendRequest"
        const val TYPE_CANCELLED_FRIEND_REQUEST = "cancelFriendRequest"
        const val notificationId = 110
    }

    var mManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun sendNotification(remoteMessage: RemoteMessage?) { // предварительный парс сообщения. Исходя из типа (valtype), делегирует отображение нотификации одному из методов
        Log.d("Egor", "NotificationHelper sendNotification()")
        if (remoteMessage?.data == null) {
            return
        }

        val message = remoteMessage.data[MESSAGE]
        val jsonMessage = JSONObject(message).getJSONObject(JSON_MESSAGE)

        val type = jsonMessage.getString(TYPE)
        when (type) {
            TYPE_ADD_FRIEND -> sendAddFriendNotification(jsonMessage)
            TYPE_APPROVED_FRIEND -> sendApprovedFriendNotification(jsonMessage)
            TYPE_CANCELLED_FRIEND_REQUEST -> sendCancelledFriendNotification(jsonMessage)
        }
    }

    private fun sendAddFriendNotification(jsonMessage: JSONObject) {
        Log.d("Egor", "NotificationHelper sendAddFriendNotification()")
        val friend = parseFriend(jsonMessage)

        showMessage("${friend.name} ${context.getString(R.string.wants_add_as_friend)}")
    }


    private fun sendApprovedFriendNotification(jsonMessage: JSONObject) {
        Log.d("Egor", "NotificationHelper sendApprovedFriendNotification()")
        val friend = parseFriend(jsonMessage)

        showMessage("${friend.name} ${context.getString(R.string.approved_friend_request)}")
    }

    private fun sendCancelledFriendNotification(jsonMessage: JSONObject) {
        Log.d("Egor", "NotificationHelper sendCancelledFriendNotification()")
        val friend = parseFriend(jsonMessage)

        showMessage("${friend.name} ${context.getString(R.string.cancelled_friend_request)}")
    }


    private fun parseFriend(jsonMessage: JSONObject): FriendEntity {
        Log.d("Egor", "NotificationHelper parseFriend()")

        val requestUser = if (jsonMessage.has(ApiService.PARAM_REQUEST_USER)) {
            jsonMessage.getJSONObject(ApiService.PARAM_REQUEST_USER)
        } else {
            jsonMessage.getJSONObject(ApiService.PARAM_APPROVED_USER)
        }

        val friendsId = jsonMessage.getLong(ApiService.PARAM_FRIENDS_ID)

        val id = requestUser.getLong(ApiService.PARAM_USER_ID)
        val name = requestUser.getString(ApiService.PARAM_NAME)
        val email = requestUser.getString(ApiService.PARAM_EMAIL)
        val status = requestUser.getString(ApiService.PARAM_STATUS)
        val image = requestUser.getString(ApiService.PARAM_USER_ID)

        return FriendEntity(id, name, email, friendsId, status, image)
    }

    private fun showMessage(message: String) {
        Log.d("Egor", "NotificationHelper showMessage()")
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // create android channel
            val androidChannel = NotificationChannel(
                    context.packageName,
                    "${context.packageName}.notification_chanel", NotificationManager.IMPORTANCE_DEFAULT
            )
            // Sets whether notifications posted to this channel should display notification lights
            androidChannel.enableLights(true)
            // Sets whether notification posted to this channel should vibrate.
            androidChannel.enableVibration(true)
            // Sets the notification light color for notifications posted to this channel
            androidChannel.lightColor = Color.GREEN
            // Sets whether notifications posted to this channel appear on the lockscreen or not
            androidChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

            mManager.createNotificationChannel(androidChannel)
        }
    }

}