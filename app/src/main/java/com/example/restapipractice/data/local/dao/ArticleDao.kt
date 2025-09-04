package com.example.restapipractice.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.restapipractice.data.local.entry.News.Article
import com.example.restapipractice.data.local.entry.News.ArticleWithItems
import com.example.restapipractice.data.local.entry.News.Items

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<Items>)

    @Transaction
    @Query("SELECT * FROM articles WHERE id = :articleId")
    suspend fun getArticleWithItems(articleId: Int): ArticleWithItems

    @Transaction
    @Query("SELECT * FROM items WHERE id = :itemId")
    suspend fun getItemsByItemId(itemId: Int): Items

    @Transaction
    @Query("SELECT * FROM items")
    suspend fun getAllItems(): List<Items>
}