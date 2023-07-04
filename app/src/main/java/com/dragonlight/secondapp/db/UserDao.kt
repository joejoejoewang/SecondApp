package com.dragonlight.secondapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dragonlight.secondapp.db.entity.ImportantLevel
import com.dragonlight.secondapp.db.entity.ImportantLevelWithUser
import com.dragonlight.secondapp.db.entity.Task
import com.dragonlight.secondapp.db.entity.User

@Dao
interface UserDao {
    //contact
    @Query("SELECT * FROM User WHERE userName LIKE :search")
    fun searchContactName(search: String): LiveData<List<User>>

    //User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
    @Update
    suspend fun updateUser(user: User)
//    @Delete
//    suspend fun deleteUser(user: User)
    @Query("SELECT * FROM user ORDER BY userName DESC")
    fun loadAllUser(): LiveData<List<User>>
    @Query("SELECT * FROM user WHERE userId =:search")
    fun searchUserById(search: Int): LiveData<User>


    //ImportantLevel
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImportantLevel(level: ImportantLevel)
    @Update
    suspend fun updateImportantLevel(level: ImportantLevel)
    @Delete
    suspend fun deleteImportantLevel(level: ImportantLevel)
    @Query("SELECT * FROM ImportantLevel ORDER BY levelId DESC")
    fun loadAllImportantLevel(): LiveData<List<ImportantLevel>>
    @Query("SELECT * FROM ImportantLevel WHERE levelGroup =:search")
    fun searchLevelByName(search: String): LiveData<ImportantLevel>
    @Query("SELECT * FROM ImportantLevel WHERE levelId =:search")
    fun searchLevelById(search: Int): LiveData<ImportantLevel>


    //Task
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)
    @Update
    suspend fun updateTask(task: Task)
    @Delete
    suspend fun deleteTask(task: Task)
    @Query("SELECT * FROM Task WHERE userId =:search ORDER BY date DESC")
    fun loadAllTaskWithUser(search: Int): LiveData<List<Task>>


    //ImportantLevelWithUser
    @Query("SELECT * FROM ImportantLevel INNER JOIN User ON ImportantLevel.levelId = User.levelId Where ImportantLevel.levelId =:search")
    fun getLevelWithUser(search: Int): LiveData<MutableList<ImportantLevelWithUser>>

}