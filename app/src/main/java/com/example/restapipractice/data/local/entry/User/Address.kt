package com.example.restapipractice.data.local.entry.User

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "addresses",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class Address(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String
)