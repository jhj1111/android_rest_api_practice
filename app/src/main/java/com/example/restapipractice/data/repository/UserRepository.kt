package com.example.restapipractice.data.repository

import com.example.restapipractice.data.local.entry.User.UserWithDetailsDTO
import com.example.restapipractice.data.remote.RetrofitClient

class UserRepository {
    suspend fun getUsers(): List<UserWithDetailsDTO> {
        return RetrofitClient.api.getUsers()
    }
}