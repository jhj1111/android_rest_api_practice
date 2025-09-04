package com.example.restapipractice.data.local.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.restapipractice.data.local.entry.User.Address
import com.example.restapipractice.data.local.entry.User.AddressWithGeo
import com.example.restapipractice.data.local.entry.User.Company
import com.example.restapipractice.data.local.entry.User.Geo
import com.example.restapipractice.data.local.entry.User.User
import com.example.restapipractice.data.local.entry.User.UserWithDetails

@Dao
interface UserDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertUsersWithDetails(users: List<UserWithDetails>)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAddress(address: Address)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertGeo(geo: Geo)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertCompany(company: Company)

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserWithDetails(userId: Int): UserWithDetails

    @Transaction
    @Query("SELECT * FROM addresses WHERE id = :addressId")
    suspend fun getAddressWithGeo(addressId: Int): AddressWithGeo
}