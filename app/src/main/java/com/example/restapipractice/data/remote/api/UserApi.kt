package com.example.restapipractice.data.remote.api

import com.example.restapipractice.data.local.entry.User.UserWithDetailsDTO
import retrofit2.http.GET

interface UserApi {
    @GET("users")
    suspend fun getUsers(): List<UserWithDetailsDTO>
}

