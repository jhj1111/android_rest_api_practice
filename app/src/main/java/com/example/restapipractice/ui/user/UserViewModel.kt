package com.example.restapipractice.ui.user

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapipractice.data.local.AppDatabase
import com.example.restapipractice.data.local.entry.User.Address
import com.example.restapipractice.data.local.entry.User.Company
import com.example.restapipractice.data.local.entry.User.Geo
import com.example.restapipractice.data.local.entry.User.User
import com.example.restapipractice.data.local.entry.User.UserWithDetails
import com.example.restapipractice.data.remote.api.UserWithDetailsDTO
import com.example.restapipractice.data.repository.UserRepository
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

        val geo = Geo(
            addressId = address.id,
            lat = addressDTO?.geo?.lat,
            lng = addressDTO?.geo?.lng
        )
        println("geo : $geo")

        val userWithDetails = UserWithDetails(
            user = user,
            address = address,
            company = company,
            geo = geo
        )

        viewModelScope.launch {
            try {
                _userDao.insert(userWithDetails)
//                _userDao.insert(geo)
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
                    val userWithDetails = UserWithDetails(
                        user = it.user,
                        address = it.address,
                        company = it.company,
                        geo = it.geo
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
}