package com.example.restapipractice.data.repository

import com.example.restapipractice.data.remote.api.UserWithDetailsDTO
import com.example.restapipractice.data.remote.RetrofitClient

class UserRepository {
    suspend fun getUsers(): List<UserWithDetailsDTO> {
        return RetrofitClient.api.getUsers()
    }
}