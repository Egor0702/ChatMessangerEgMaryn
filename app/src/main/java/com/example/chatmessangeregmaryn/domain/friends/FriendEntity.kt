package com.example.chatmessangeregmaryn.domain.friends

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class FriendEntity ( // data class what contains a friend's data
        @PrimaryKey // id теперь ключ к таблице
        @SerializedName("user_id")
        var id: Long, // id fiends
        var name: String, // name friends
        var email: String, // email friends
        @ColumnInfo(name = "friends_id") // ПРИСВОИМ имена полям таблицы
        @SerializedName("friends_id")
        var friendsId: Long,
        var status: String, // stats friends
        var image: String, // image friends
        @ColumnInfo (name ="is_request") // присваиваем имена полям таблицы
        var isRequests: Int = 0 // данная переменная нужна, чтобы определить: явялется ли ли FriendEntity другом(= 0) или запросом в друзья(= 1)
)