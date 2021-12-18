package com.example.chatmessangeregmaryn.domain.friends

import com.google.gson.annotations.SerializedName

data class FriendEntity ( // data class what contains a friend's datd
        @SerializedName("user_id")
        val id: Long, // id fiends
        val name: String, // name friends
        val email: String, // email friends
        @SerializedName("friends_id")
        val friendsId: Long,
        val status: String, // stats friends
        val image: String // image friends
)