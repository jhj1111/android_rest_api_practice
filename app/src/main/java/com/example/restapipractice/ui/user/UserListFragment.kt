package com.example.restapipractice.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.restapipractice.data.local.entry.User.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
//    users: List<User>,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier,
) {
    val users = userViewModel.userList

    Scaffold(
        topBar = { TopAppBar(title = { Text("User List") }) },
        content = { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(users) { user ->
                    Card(
                        modifier = Modifier.padding(8.dp),
                        onClick = { /* Handle item click */ }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
//                            println(user)
//                            Text(name ?: "no name")
                            Text(user.name ?: "no name")
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
                    }
                }
            }
        }
    )
}