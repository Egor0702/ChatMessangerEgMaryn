package com.example.chatmessangeregmaryn.data.friends

import com.example.chatmessangeregmaryn.domain.friends.FriendEntity

interface FriendsCache {
    fun saveFriend(entity: FriendEntity)

    fun getFriend(key: Long): FriendEntity?

    fun getFriends(): List<FriendEntity>

    fun getFriendRequests(): List<FriendEntity>

    fun removeFriendEntity(key: Long)
}