package com.example.chatmessangeregmaryn.cache

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chatmessangeregmaryn.cache.friends.FriendsDao
import com.example.chatmessangeregmaryn.domain.friends.FriendEntity

@Database(entities = [FriendEntity::class], version = 2, exportSchema = false)
abstract class ChatDatabase : RoomDatabase() {
    abstract val friendsDao: FriendsDao
init {
    Log.d("Egor", "Всем хло, мы в ChatDatabase")
}
    companion object {
        @Volatile // обеспечиваем актуальной информацией по данной переменной все потоки
        private var INSTANCE: ChatDatabase? = null

        @Synchronized
         fun  getInstance (context: Context): ChatDatabase { // возвращаем сиглтон ChatDateBase
            Log.d("Egor", "ChatDatabase getInstance")
            var instance = INSTANCE
            if (instance == null) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                                context.applicationContext,
                                ChatDatabase::class.java,
                                "chat_database"
                        )
                                .fallbackToDestructiveMigration()
                                .build()
                        INSTANCE = instance
                    }
                    }
            return instance
        }
    }
}