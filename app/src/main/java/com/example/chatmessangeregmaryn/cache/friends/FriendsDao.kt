package com.example.chatmessangeregmaryn.cache.friends

import androidx.room.*
import com.example.chatmessangeregmaryn.data.friends.FriendsCache
import com.example.chatmessangeregmaryn.domain.friends.FriendEntity

@Dao
interface FriendsDao : FriendsCache { // класс для формирования SQL запросов
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(friendEntity: FriendEntity): Long

    @Update
    fun update(friendEntity: FriendEntity)

    @Transaction
    override fun saveFriend(entity: FriendEntity) { // сохраняем друга в БД
        if (insert(entity) == -1L) { // добавялем в таблицу нового друга, если в таблице есть такой ключ, то
            update(entity) // обновляем его
        }
    }

    @Query("SELECT * from friends_table WHERE id = :key")
    override fun getFriend(key: Long): FriendEntity? // формирует запрос для получения друга из бд по идентификатору

    @Query("SELECT * from friends_table WHERE is_request = 0")
    override fun getFriends(): List<FriendEntity> // формирует запрос для получения списка друзей текущего пользователя.

    @Query("SELECT * from friends_table WHERE is_request = 1") // формирует запрос для получения список входящих приглашений на добавление в друзья.
    override fun getFriendRequests(): List<FriendEntity> // Передает условие is_request = 1 (является приглашением в друзья).

    @Query("DELETE FROM friends_table WHERE id = :key") // формирует запрос для удаления друга из бд по идентификатору.
    override fun removeFriendEntity(key: Long)
}