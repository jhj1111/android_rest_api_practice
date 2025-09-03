package com.example.restapipractice.ui.user

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapipractice.data.local.entry.User
import com.example.restapipractice.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val _userRepository = UserRepository()
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
}