package com.example.restapipractice.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.restapipractice.data.local.entry.User.UserWithDetails
import com.example.restapipractice.data.remote.api.UserWithDetailsDTO
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSavedScreen(
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val users = remember { mutableStateListOf<UserWithDetailsDTO>() }
    LaunchedEffect(Unit) {
        scope.launch {
            userViewModel.getUsersWithDetailDTO().collect {
                users.clear()
                users.addAll(it)
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("User Saved") }) },
        content = { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(users) { user ->
                    Row(modifier = Modifier.padding(8.dp)) {
                        Card(
                            modifier = Modifier.padding(8.dp),
                            onClick = {

                            }
                        ) {
//                        Text(user.toString())
                            Text(user.name ?: "no name")
                            Text(user.username ?: "no username")
                            Text(user.email ?: "no email")
                            Text(user.phone ?: "no phone")
                            Text(user.website ?: "no website")
                            Text(user.address?.city ?: "no address")
                            Text(user.company?.name ?: "no company")
                            Text(user.company?.catchPhrase ?: "no catchPhrase")
                            Text(user.company?.bs ?: "no bs")
                            Text(user.address?.geo?.lat ?: "no lat")
                            Text(user.address?.geo?.lng ?: "no lng")
                        }
                        IconButton(
                            onClick = {
                                scope.launch {
                                    userViewModel.deleteUser(user)
                                    users.remove(user)
                                }
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }

                    }
                }
            }
        }
    )
}