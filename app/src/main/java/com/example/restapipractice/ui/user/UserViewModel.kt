package com.example.restapipractice.ui.user

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapipractice.data.local.AppDatabase
import com.example.restapipractice.data.local.entry.User.Address
import com.example.restapipractice.data.local.entry.User.Company
import com.example.restapipractice.data.local.entry.User.Geo
import com.example.restapipractice.data.local.entry.User.User
import com.example.restapipractice.data.local.entry.User.UserWithDetails
import com.example.restapipractice.data.remote.api.AddressDto
import com.example.restapipractice.data.remote.api.CompanyDto
import com.example.restapipractice.data.remote.api.GeoDto
import com.example.restapipractice.data.remote.api.UserWithDetailsDTO
import com.example.restapipractice.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val _userRepository = UserRepository()
    private val _userDao = AppDatabase.getInstance(application).userDao()
    var userList = mutableStateListOf<UserWithDetailsDTO>()
        private set

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val users = _userRepository.getUsers()
                userList.clear()
                userList.addAll(users)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getUsersWithDetail(): List<UserWithDetails> {
        try {
            val users = _userDao.getAllUsersWithDetails()
//            val userList = mutableListOf<UserWithDetails>()
//            users.forEach {
//                    val userWithDetailsDTO = UserWithDetailsDTO(
//                        id = it.user.id,
//                        name = it.user.name,
//                        username = it.user.username,
//                        email = it.user.email,
//                        phone = it.user.phone,
//                        website = it.user.website,
//
//                        address = AddressDto(
//                            street = it.address?.street,
//                            suite = it.address?.suite,
//                            city = it.address?.city,
//                            zipcode = it.address?.zipcode,
//                            geo = GeoDto(
//                                lat = it.geo?.lat,
//                                lng = it.geo?.lng
//                            )
//                        ),
//                        company = CompanyDto(
//                            name = it.company?.name,
//                            catchPhrase = it.company?.catchPhrase,
//                            bs = it.company?.bs
//                        )
//                    )
//                userList.add(it)
//            }
//                userList.clear()
//                userList.addAll(userList)
            return users
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        viewModelScope.launch {
//        }
        return emptyList()
    }

    suspend fun getUsersWithDetailDTO(): StateFlow<List<UserWithDetailsDTO>> {
        try {
            val users = _userDao.getAllUsersWithDetails()
            val userList = mutableListOf<UserWithDetailsDTO>()
            users.forEach {
                val addressWithGeo = _userDao.getUserAddressWithGeo(it.user.id)
//                println("user = $it")
//                println("addressWithGeo = $addressWithGeo")
                val userWithDetailsDTO = UserWithDetailsDTO(
                    id = it.user.id,
                    name = it.user.name,
                    username = it.user.username,
                    email = it.user.email,
                    phone = it.user.phone,
                    website = it.user.website,
                    address = AddressDto(
                        street = it.address?.street,
                        suite = it.address?.suite,
                        city = it.address?.city,
                        zipcode = it.address?.zipcode,
                        geo = GeoDto(
                            lat = addressWithGeo?.geo?.lat,
                            lng = addressWithGeo?.geo?.lng
                        )
                    ),
                    company = CompanyDto(
                        name = it.company?.name,
                        catchPhrase = it.company?.catchPhrase,
                        bs = it.company?.bs
                    )
                )
                userList.add(userWithDetailsDTO)
            }
//            userList.clear()
//            userList.addAll(userList)
//            println("userList = $userList")
            val stateFlow = MutableStateFlow(userList)
            stateFlow.value = userList
            return stateFlow
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return MutableStateFlow(emptyList())
    }

    fun insertUsersWithDetails(usersWithDetailDTO: UserWithDetailsDTO, doFetch: Boolean = false) {
        val user = User(
            id = usersWithDetailDTO.id,
            name = usersWithDetailDTO.name,
            username = usersWithDetailDTO.username,
            email = usersWithDetailDTO.email,
            phone = usersWithDetailDTO.phone,
            website = usersWithDetailDTO.website
        )
        val addressDTO = usersWithDetailDTO.address
        val companyDTO = usersWithDetailDTO.company
        val address = Address(
            userId = user.id,
            street = addressDTO?.street,
            suite = addressDTO?.suite,
            city = addressDTO?.city,
            zipcode = addressDTO?.zipcode
        )
        val company = Company(
            userId = user.id,
            name = companyDTO?.name,
            catchPhrase = companyDTO?.catchPhrase,
            bs = companyDTO?.bs
        )

//        val geo = Geo(
//            addressId = address.id,
//            lat = addressDTO?.geo?.lat,
//            lng = addressDTO?.geo?.lng
//        )
//        println("geo : $geo")

        val userWithDetails = UserWithDetails(
            user = user,
            address = address,
            company = company,
//            geo = geo
        )

        viewModelScope.launch {
            try {
                val userAdressId = _userDao.insert(userWithDetails)
                val geo = Geo(
                    addressId = userAdressId.second.toInt(),
                    lat = addressDTO?.geo?.lat,
                    lng = addressDTO?.geo?.lng
                )
                println("geo : $geo")
                _userDao.insert(geo)
                if (doFetch) fetchUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun insertUsersWithDetails(usersWithDetails: List<UserWithDetails>) {
        viewModelScope.launch {
            try {
                usersWithDetails.forEach {
//                    val geo = if (it.address != null) Geo(
//                        addressId = it.address.id,
//                        lat = it.address.geo?.lat,
//                        lng = it.address.geo?.lng
//                    )
                    val userWithDetails = UserWithDetails(
                        user = it.user,
                        address = it.address,
                        company = it.company,
//                        geo = it.geo
                    )
                    _userDao.insert(userWithDetails)
                }
                fetchUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            try {
                _userDao.insert(user)
                fetchUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteUser(user: User, doFetch: Boolean = false) {
        viewModelScope.launch {
            try {
                _userDao.delete(user)
                if (doFetch) fetchUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun deleteUser(user: UserWithDetailsDTO, doFetch: Boolean = false) {
        viewModelScope.launch {
            try {
                val user = getUsersWithDetail().find { it.user.id == user.id }
                if (user != null) {
                    _userDao.delete(user.user)
                }
                if (doFetch) fetchUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}