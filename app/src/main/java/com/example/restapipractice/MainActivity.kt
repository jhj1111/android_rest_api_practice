package com.example.restapipractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restapipractice.ui.theme.RestApiPracticeTheme
import com.example.restapipractice.ui.user.UserListScreen
import com.example.restapipractice.ui.user.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val userViewModel: UserViewModel = viewModel()
            LaunchedEffect(Unit) {
                userViewModel.fetchUsers()
            }
            println(userViewModel.userList)
            RestApiPracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserListScreen(
                        users = userViewModel.userList,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


