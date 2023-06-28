package com.dragonlight.secondapp.db

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dragonlight.secondapp.db.entity.User

interface UserDao {

    //User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)


    @Query("SELECT * FROM user ORDER BY userName DESC")
    fun loadAllUser(): LiveData<List<User>>

}