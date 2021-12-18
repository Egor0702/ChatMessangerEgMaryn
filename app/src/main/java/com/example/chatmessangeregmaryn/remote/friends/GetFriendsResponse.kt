package com.example.chatmessangeregmaryn.remote.friends

import com.example.chatmessangeregmaryn.domain.friends.FriendEntity
import com.example.chatmessangeregmaryn.remote.core.BaseResponse

class GetFriendsResponse (
        success: Int,
        message: String,
        val friends: List<FriendEntity>
) : BaseResponse(success, message)