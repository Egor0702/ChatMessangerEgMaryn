package com.example.chatmessangeregmaryn.remote.friends

import com.example.chatmessangeregmaryn.domain.friends.FriendEntity
import com.example.chatmessangeregmaryn.remote.core.BaseResponse
import com.google.gson.annotations.SerializedName

class GetFriendRequestsResponse (
        success: Int,
        message: String,
        @SerializedName("friend_requests")
        val friendsRequests: List<FriendEntity>
) : BaseResponse(success, message)