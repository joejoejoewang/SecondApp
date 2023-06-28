package com.dragonlight.secondapp.viewmodel

import androidx.lifecycle.LiveData
import com.dragonlight.secondapp.db.UserDao
import com.dragonlight.secondapp.db.entity.User

class UserRepository(private var userDao: UserDao) {

    //user
    val loadAllUser: LiveData<List<User>> = userDao.loadAllUser()

    suspend fun insertUserRe(user: User){userDao.insertUser(user)}
    suspend fun updateUserRe(user: User){userDao.updateUser(user)}
}