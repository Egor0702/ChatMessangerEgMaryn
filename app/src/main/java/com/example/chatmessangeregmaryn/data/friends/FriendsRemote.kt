package com.example.chatmessangeregmaryn.data.friends

import com.example.chatmessangeregmaryn.domain.friends.FriendEntity
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.type.None

interface FriendsRemote { // contains function for interacting with friends on the server
    fun getFriends(userId: Long, token: String): Either<Failure, List<FriendEntity>> // get list of friends of current user
    fun getFriendRequests(userId: Long, token: String): Either<Failure, List<FriendEntity>> // getting a list of incoming invitations to add to friends

    fun approveFriendRequest(userId: Long, requestUserId: Long, friendsId: Long, token: String): Either<Failure, None> // accepts an invitation to add friends.
    fun cancelFriendRequest(userId: Long, requestUserId: Long, friendsId: Long, token: String): Either<Failure, None> // rejects an invitation to add friends.

    fun addFriend(email: String, userId: Long, token: String): Either<Failure, None> // send an invitation to add to friends
    fun deleteFriend(userId: Long, requestUserId: Long, friendsId: Long, token: String): Either<Failure, None> // remove from list of friends
}