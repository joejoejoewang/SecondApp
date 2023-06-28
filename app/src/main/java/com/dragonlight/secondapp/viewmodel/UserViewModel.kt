package com.dragonlight.secondapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dragonlight.secondapp.db.UserDatabase
import com.dragonlight.secondapp.db.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(app: Application): AndroidViewModel(app) {
    val readAllUser: LiveData<List<User>>
    private val userRepository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(app.applicationContext).uerDao()
        userRepository = UserRepository(userDao)
        readAllUser = userRepository.loadAllUser
    }


    //user
    fun insertUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            userRepository.insertUserRe(user)
        }
    }

    fun updateUser(user: User){
        viewModelScope.launch {
            userRepository.updateUserRe(user)
        }
    }
}