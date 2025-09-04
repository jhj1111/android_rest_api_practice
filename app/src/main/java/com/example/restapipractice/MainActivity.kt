package com.example.restapipractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.restapipractice.navigation.AppNavigation
import com.example.restapipractice.ui.theme.RestApiPracticeTheme
import com.example.restapipractice.ui.user.UserViewModel

data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String = label
)

const val USER_LIST_ROUTE = "user_list"
const val ARTICLE_LIST_ROUTE = "article_list"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val userViewModel: UserViewModel = viewModel()
    val navController = rememberNavController()
    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            label = "User",
            icon = Icons.Filled.Person,
            route = USER_LIST_ROUTE
        ),
        BottomNavigationItem(
            label = "Article",
            icon = Icons.Filled.Info,
            route = ARTICLE_LIST_ROUTE
        )
    )

    LaunchedEffect(Unit) {
        userViewModel.fetchUsers()
    }
    println(userViewModel.userList)
    RestApiPracticeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar {
                    bottomNavigationItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label)  },
                            label = { Text(text = item.label) },
                            selected = navController.currentDestination?.route == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }) {
            AppNavigation(
                navController = navController,
                modifier = Modifier.padding(it),
                userViewModel = userViewModel,
                articleViewModel = viewModel(),
            )
        }
    }
}
