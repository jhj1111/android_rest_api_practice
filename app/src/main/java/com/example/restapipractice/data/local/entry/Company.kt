package com.example.restapipractice.data.local.entry

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

//@Entity(
//    tableName = "companies",
//    foreignKeys = [
//        ForeignKey(
//            entity = User::class,
//            parentColumns = ["id"],
//            childColumns = ["userId"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ],
//    indices = [Index("userId")]
//)
//data class Company(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val userId: Int,
//    val name: String,
//    val catchPhrase: String,
//    val bs: String
//)