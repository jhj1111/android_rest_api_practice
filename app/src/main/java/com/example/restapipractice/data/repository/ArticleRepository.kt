package com.example.restapipractice.data.repository

import com.example.restapipractice.data.remote.ArticleRetrofit
import com.example.restapipractice.data.remote.api.ArticleDto
import retrofit2.http.Query

class ArticleRepository {
    suspend fun getArticles(
        query: String,
        display: Int = 10,
        start: Int = 1,
        sort: String = "sim",   // sim: 유사도순, date: 날짜순
    ): ArticleDto {
        return ArticleRetrofit.api.getArticles(
            "4N2f4cbfAPyksQtpXunm",
            "tWkcbkE4mY",
            query,
            display,
            start,
            sort
            )
    }
}