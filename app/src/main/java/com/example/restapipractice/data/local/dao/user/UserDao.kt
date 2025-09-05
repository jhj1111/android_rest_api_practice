package com.example.restapipractice.data.local.dao.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.restapipractice.data.local.entry.User.Address
import com.example.restapipractice.data.local.entry.User.AddressWithGeo
import com.example.restapipractice.data.local.entry.User.Company
import com.example.restapipractice.data.local.entry.User.Geo
import com.example.restapipractice.data.local.entry.User.User
import com.example.restapipractice.data.local.entry.User.UserWithDetails
import com.example.restapipractice.data.remote.api.AddressDto
import com.example.restapipractice.data.remote.api.UserWithDetailsDTO

@Dao
interface UserDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertUsersWithDetails(users: List<UserWithDetails>)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(user: User): Long

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(address: Address): Long

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(geo: Geo): Long

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(company: Company): Long

    @Update
    suspend fun update(user: User)

    @Update
    suspend fun update(address: Address)

    @Update
    suspend fun update(geo: Geo)

    @Update
    suspend fun update(company: Company)

    @Transaction
    @Delete
    suspend fun delete(user: User)

    @Transaction
    @Query("SELECT * FROM users")
    suspend fun getAllUsersWithDetails(): List<UserWithDetails>

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserWithDetails(userId: Int): UserWithDetails

    @Transaction
    @Query("""
        SELECT * FROM addresses
        INNER JOIN users ON users.id = addresses.userId
        INNER JOIN geo ON addresses.id = geo.addressId
        WHERE users.id = :userId
    """)
    suspend fun getUserAddressWithGeo(userId: Int): AddressWithGeo?

    @Transaction
    @Query("SELECT * FROM addresses")
    suspend fun getAddressWithGeo(): AddressWithGeo?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(userWithDetails: UserWithDetails): Pair<Long, Long> {
        val userId = insert(userWithDetails.user)
        val addressId = insert(userWithDetails.address!!)
//        val geo = Geo(
//            addressId = addressId.toInt(),
//            lat = userWithDetails.geo?.lat,
//            lng = userWithDetails.geo?.lng
//        )
//        insert(geo)
        insert(userWithDetails.company!!)
        return Pair(userId, addressId)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(users: List<UserWithDetails>) {
        users.forEach {
            insert(it)
        }
    }

    @Transaction
    @Update
    suspend fun update(userId: Int, isFavorite: Boolean) {
        var user = getUserWithDetails(userId)
        user = user.copy(user = user.user.copy(isFavorite = isFavorite))
        update(user.user)
        update(user.address!!)
        update(user.company!!)
    }

    @Transaction
    @Update
    suspend fun update(users: List<UserWithDetails>) {
        users.forEach {
            update(it)
        }
    }

    @Transaction
    @Update
    suspend fun update(userWithDetails: UserWithDetails) {
        update(userWithDetails.address!!)
        update(userWithDetails.company!!)
        update(userWithDetails.user)
    }

    @Transaction
    @Delete
    suspend fun delete(users: List<User>) {
        users.forEach {
            delete(it)
        }
    }
}