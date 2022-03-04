package com.example.chatmessangeregmaryn.domain.friends

import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.type.None

interface FriendsRepository { // iis interface needed for interaction with friends (adding friend, deleting and t.c.)
    fun getFriends(needFetch: Boolean): Either<Failure, List<FriendEntity>> // get list of friends of current user
    fun getFriendRequests(needFetch: Boolean): Either<Failure, List<FriendEntity>> // getting a list of incoming invitations to add to friends

    fun approveFriendRequest(friendEntity: FriendEntity): Either<Failure, None> // accepts an invitation to add friends.
    fun cancelFriendRequest(friendEntity: FriendEntity): Either<Failure, None> // rejects an invitation to add friends.

    fun addFriend(email: String): Either<Failure, None> // send an invitation to add to friends
    fun deleteFriend(friendEntity: FriendEntity): Either<Failure, None> // remove from list of friends
}