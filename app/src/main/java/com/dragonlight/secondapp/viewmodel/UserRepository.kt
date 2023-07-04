package com.dragonlight.secondapp.viewmodel

import android.accessibilityservice.AccessibilityService.TakeScreenshotCallback
import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.lifecycle.LiveData
import com.dragonlight.secondapp.db.UserDao
import com.dragonlight.secondapp.db.entity.ImportantLevel
import com.dragonlight.secondapp.db.entity.Task
import com.dragonlight.secondapp.db.entity.User

class UserRepository(private var userDao: UserDao) {
    //contact
    fun searchContactNameRe(search: String): LiveData<List<User>>{return userDao.searchContactName(search)}

    //user
    val loadAllUser: LiveData<List<User>> = userDao.loadAllUser()

    suspend fun insertUserRe(user: User){userDao.insertUser(user)}
    suspend fun updateUserRe(user: User){userDao.updateUser(user)}
//    suspend fun deleteUserRe(user: User){userDao.deleteUser(user)}

    fun searchUserByIdRe(search: Int): LiveData<User>{return userDao.searchUserById(search)}


    //ImportantLevel
    val loadAllImportantLevel: LiveData<List<ImportantLevel>> = userDao.loadAllImportantLevel()

    suspend fun insertImportantLevelRe(level: ImportantLevel){userDao.insertImportantLevel(level)}
    suspend fun updateImportantLevelRe(level: ImportantLevel){userDao.updateImportantLevel(level)}
    suspend fun deleteImportantLevelRe(level: ImportantLevel){userDao.deleteImportantLevel(level)}

    fun searchLevelByIdRe(search: Int): LiveData<ImportantLevel>{return userDao.searchLevelById(search)}
    fun searchLevelByNameRe(search: String): LiveData<ImportantLevel>{return userDao.searchLevelByName(search)}


    //Task
    suspend fun insertTaskRe(task: Task){userDao.insertTask(task)}
    suspend fun updateTaskRe(task: Task){userDao.updateTask(task)}
    suspend fun deleteTaskRe(task: Task){userDao.deleteTask(task)}

    fun loadAllTaskWithUser(search: Int): LiveData<List<Task>>{return userDao.loadAllTaskWithUser(search)}
}