package com.dragonlight.secondapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dragonlight.secondapp.db.entity.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun uerDao(): UserDao

    companion object{
        private var INSTANCE: UserDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): UserDatabase{
            INSTANCE?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                UserDatabase::class.java,
                "userDB").build().apply {
                    INSTANCE = this
            }
        }
    }
}