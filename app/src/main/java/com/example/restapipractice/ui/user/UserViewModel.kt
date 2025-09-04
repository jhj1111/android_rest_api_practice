package com.example.restapipractice.ui.user

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapipractice.data.local.AppDatabase
import com.example.restapipractice.data.local.entry.User.User
import com.example.restapipractice.data.local.entry.User.UserWithDetails
import com.example.restapipractice.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val _userRepository = UserRepository()
    private val _userDao = AppDatabase.getInstance(application).userDao()
    var userList = mutableStateListOf<User>()
        private set

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val users = _userRepository.getUsers()
                userList.clear()
                userList.addAll(users)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

//    fun insertUsersWithDetails(usersWithDetails: List<UserWithDetails>) {
//        viewModelScope.launch {
//            try {
//                _userDao.insertUsersWithDetails(usersWithDetails)
//                fetchUsers()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            try {
                _userDao.insertUser(user)
                fetchUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}