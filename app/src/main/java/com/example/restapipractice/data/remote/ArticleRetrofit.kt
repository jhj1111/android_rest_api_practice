package com.example.restapipractice.data.remote

import com.example.restapipractice.data.remote.api.ArticleApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ArticleRetrofit {
    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val api: ArticleApi =
        retrofit.create(ArticleApi::class.java)
}