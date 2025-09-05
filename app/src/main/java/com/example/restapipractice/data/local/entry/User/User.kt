package com.example.restapipractice.data.local.entry.User

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val name: String?,
    val username: String?,
    val email: String?,
    val phone: String?,
    val website: String?,
)


data class UserWithDetails(
    @Embedded
    val user: User,

    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val address: Address?,

//    @Relation(
//        parentColumn = "id",
//        entityColumn = "addressId"
//        )
//    val geo: Geo?,

    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val company: Company?
)

data class AddressWithGeo(
    @Embedded val address: Address,

    @Relation(
        parentColumn = "id",
        entityColumn = "addressId"
    )
    val geo: Geo?
)
