package com.example.restapipractice.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.restapipractice.data.local.dao.user.UserDao
//import com.example.restapipractice.data.local.dao.user.UserWithDetailsDao
import com.example.restapipractice.data.local.entry.User.Address
import com.example.restapipractice.data.local.entry.User.Company
import com.example.restapipractice.data.local.entry.User.Geo
import com.example.restapipractice.data.local.entry.User.User

@Database(
    entities = [User::class, Address::class, Geo::class, Company::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
//    abstract fun addressDao(): AddressDao
//    abstract fun geoDao(): GeoDao
//    abstract fun companyDao(): CompanyDao
//    abstract fun userWithDetailsDao(): UserWithDetailsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
