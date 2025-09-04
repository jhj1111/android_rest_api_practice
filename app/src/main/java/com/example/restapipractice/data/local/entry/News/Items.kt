package com.example.restapipractice.data.local.entry.News

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = Article::class,
            parentColumns = ["id"],
            childColumns = ["articleId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("articleId")]
)
data class Items(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val articleId: Int,
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
)