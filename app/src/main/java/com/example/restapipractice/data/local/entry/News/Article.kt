package com.example.restapipractice.data.local.entry.News

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
)

data class ArticleWithItems(
    @Embedded val article: Article,
    @Relation(
        parentColumn = "id",
        entityColumn = "articleId"
    )
    val items: List<Items>?
)