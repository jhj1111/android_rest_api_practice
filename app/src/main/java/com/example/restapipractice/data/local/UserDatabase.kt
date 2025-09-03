package com.example.restapipractice.data.local

//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.restapipractice.data.local.dao.UserDao
//import com.example.restapipractice.data.local.entry.Address
//import com.example.restapipractice.data.local.entry.Company
//import com.example.restapipractice.data.local.entry.Geo
//import com.example.restapipractice.data.local.entry.User
//
//@Database(
//    entities = [User::class, Address::class, Geo::class, Company::class],
//    version = 1,
//    exportSchema = false
//)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                INSTANCE ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "user_database"
//                ).build().also { INSTANCE = it }
//            }
//        }
//    }
//}
