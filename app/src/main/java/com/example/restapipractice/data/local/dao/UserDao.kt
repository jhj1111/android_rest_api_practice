package com.example.restapipractice.data.local.dao

//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Transaction
//import com.example.restapipractice.data.local.entry.Address
////import com.example.restapipractice.data.local.entry.AddressWithGeo
//import com.example.restapipractice.data.local.entry.Company
//import com.example.restapipractice.data.local.entry.Geo
//import com.example.restapipractice.data.local.entry.User
////import com.example.restapipractice.data.local.entry.UserWithDetails
//
//@Dao
//interface UserDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertUser(user: User)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAddress(address: Address)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertGeo(geo: Geo)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCompany(company: Company)

//    @Transaction
//    @Query("SELECT * FROM users WHERE id = :userId")
//    suspend fun getUserWithDetails(userId: Int): UserWithDetails
//
//    @Transaction
//    @Query("SELECT * FROM addresses WHERE id = :addressId")
//    suspend fun getAddressWithGeo(addressId: Int): AddressWithGeo
//}