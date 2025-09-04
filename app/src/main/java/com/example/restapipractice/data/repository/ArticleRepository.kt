package com.example.restapipractice.data.repository

import com.example.restapipractice.data.remote.ArticleRetrofit
import com.example.restapipractice.data.remote.api.ArticleDto

class ArticleRepository {
    suspend fun getArticles(): ArticleDto {
        return ArticleRetrofit.api.getArticles(
            "4N2f4cbfAPyksQtpXunm",
            "tWkcbkE4mY",
            "new"
            )
    }
}