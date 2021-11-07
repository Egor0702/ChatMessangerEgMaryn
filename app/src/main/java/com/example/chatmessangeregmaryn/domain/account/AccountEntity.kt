package com.example.chatmessangeregmaryn.domain.account

import com.google.gson.annotations.SerializedName

class AccountEntity(
    @SerializedName("user_id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("user_date")
    val userDate: Long,
    @SerializedName("image")
    val image: Long,
    @SerializedName("last_seen")
    val lastSeen : Long
)