package com.example.restapipractice.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

data class ArticleDto(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<ItemsDto>,
)

data class ItemsDto(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
)

interface ArticleApi {
    @GET("search/news.json")
    suspend fun getArticles(
//        @Header("4N2f4cbfAPyksQtpXunm") apiKey: String,
        @Header("X-Naver-Client-Id") clientId: String,   // X-Naver-Client-Id
        @Header("X-Naver-Client-Secret") clientSecret: String,     // tWkcbkE4mY
        @Query("query") query: String,
    ): ArticleDto
}