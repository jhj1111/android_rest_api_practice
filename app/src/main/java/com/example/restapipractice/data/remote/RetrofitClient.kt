package com.example.restapipractice.data.remote

import com.example.restapipractice.data.remote.api.UserApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }
}