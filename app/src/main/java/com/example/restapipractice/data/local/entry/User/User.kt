package com.example.restapipractice.data.local.entry.User

import androidx.room.Embedded
import androidx.room.Entity
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

// API 응답의 Geo 구조를 위한 DTO
data class GeoDto(
    val lat: String?,
    val lng: String?
)

// API 응답의 Address 구조를 위한 DTO
data class AddressDto(
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?,
    val geo: GeoDto?
)

// API 응답의 Company 구조를 위한 DTO
data class CompanyDto(
    val name: String?,
    val catchPhrase: String?,
    val bs: String?
)

data class UserWithDetailsDTO(
    // 사용자 정보 필드들이 최상위 레벨에 직접 존재
    val id: Int,
    val name: String?,
    val username: String?,
    val email: String?,
    val phone: String?,
    val website: String?,

    // 중첩된 객체들
    val address: AddressDto?,
    val company: CompanyDto?
)


data class UserWithDetails(
    @Embedded
    val user: User,

    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val address: Address?,

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
