package com.example.restapipractice.data.local.entry.User

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "geo",
    foreignKeys = [
        ForeignKey(
            entity = Address::class,
            parentColumns = ["id"],
            childColumns = ["addressId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("addressId")]
)
data class Geo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val addressId: Int,
    val lat: String?,
    val lng: String?,
)