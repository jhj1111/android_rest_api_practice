package com.example.restapipractice.data.local.dao.user

//@Dao
//abstract class UserWithDetailsDao(
//    private val userDao: UserDao,
//    private val addressDao: AddressDao,
//    private val geoDao: GeoDao,
//    private val companyDao: CompanyDao
//) {
//    @Transaction
//    open suspend fun insertUserWithDetails(userWithDetails: UserWithDetails) {
//        val userId = userDao.insertUser(userWithDetails.user)
//        val addressId = userDao.insertAddress(userWithDetails.address?.copy(userId = userId.toInt()) ?: return)
//        userDao.insertGeo(addressId.geo?.copy(addressId = addressId.toInt()))
//        companyDao.insertCompany(userWithDetails.company.copy(userId = userId.toInt()))
//    }
//}