package com.example.restapipractice.data.repository

import com.example.restapipractice.data.local.entry.User
import com.example.restapipractice.data.remote.RetrofitClient

class UserRepository {
    suspend fun getUsers(): List<User> {
        return RetrofitClient.api.getUsers()
    }
}