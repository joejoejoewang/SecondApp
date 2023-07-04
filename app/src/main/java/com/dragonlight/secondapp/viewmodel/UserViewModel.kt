package com.dragonlight.secondapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dragonlight.secondapp.db.UserDatabase
import com.dragonlight.secondapp.db.entity.ImportantLevel
import com.dragonlight.secondapp.db.entity.Task
import com.dragonlight.secondapp.db.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(app: Application): AndroidViewModel(app) {
    val readAllUser: LiveData<List<User>>
    private val userRepository: UserRepository
    val readAllImportantLevel: LiveData<List<ImportantLevel>>

    init {
        val userDao = UserDatabase.getDatabase(app.applicationContext).uerDao()
        userRepository = UserRepository(userDao)
        readAllUser = userRepository.loadAllUser
        readAllImportantLevel = userRepository.loadAllImportantLevel

    }

    //contact
    fun searchContactName(search: String): LiveData<List<User>>{return userRepository.searchContactNameRe(search)}

    //user
    fun insertUser(user: User){viewModelScope.launch(Dispatchers.IO){userRepository.insertUserRe(user)}}
    fun updateUser(user: User){viewModelScope.launch(Dispatchers.IO){userRepository.updateUserRe(user)}}
//    fun deleteUser(user: User){viewModelScope.launch(Dispatchers.IO){userRepository.deleteUserRe(user)}}
    fun searchUserById(search: Int): LiveData<User>{return userRepository.searchUserByIdRe(search)}

    //importantLevel
    fun insertLevel(level: ImportantLevel){viewModelScope.launch(Dispatchers.IO){userRepository.insertImportantLevelRe(level)}}
    fun updateLevel(level: ImportantLevel){viewModelScope.launch(Dispatchers.IO){userRepository.updateImportantLevelRe(level)}}
    fun deleteLevel(level: ImportantLevel){viewModelScope.launch(Dispatchers.IO){userRepository.deleteImportantLevelRe(level)}}

    fun searchLevelById(search: Int): LiveData<ImportantLevel>{return userRepository.searchLevelByIdRe(search)}
    fun searchLevelByName(search: String): LiveData<ImportantLevel>{return userRepository.searchLevelByNameRe(search)}


    //Task
    fun insertTask(task: Task){viewModelScope.launch(Dispatchers.IO){userRepository.insertTaskRe(task)}}
    fun updateTask(task: Task){viewModelScope.launch(Dispatchers.IO){userRepository.updateTaskRe(task)}}
    fun deleteTask(task: Task){viewModelScope.launch(Dispatchers.IO){userRepository.deleteTaskRe(task)}}

    fun loadAllTaskWithUser(search: Int): LiveData<List<Task>>{return userRepository.loadAllTaskWithUser(search)}
}