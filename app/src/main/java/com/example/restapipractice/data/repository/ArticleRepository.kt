package com.example.restapipractice.data.repository

import com.example.restapipractice.BuildConfig // BuildConfig 임포트
import com.example.restapipractice.data.remote.ArticleRetrofit
import com.example.restapipractice.data.remote.api.ArticleDto
// import retrofit2.http.Query // ArticleRepository에서 직접 사용하지 않으므로 제거해도 무방

class ArticleRepository {
    suspend fun getArticles(
        query: String,
        display: Int = 10,
        start: Int = 1,
        sort: String = "sim",   // sim: 유사도순, date: 날짜순
    ): ArticleDto {
        return ArticleRetrofit.api.getArticles(
            clientId = BuildConfig.NAVER_CLIENT_ID, // BuildConfig 사용
            clientSecret = BuildConfig.NAVER_CLIENT_SECRET, // BuildConfig 사용
            query = query,
            display = display,
            start = start,
            sort = sort
            )
    }
}