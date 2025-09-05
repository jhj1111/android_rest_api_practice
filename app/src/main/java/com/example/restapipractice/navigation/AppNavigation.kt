package com.example.restapipractice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.restapipractice.ARTICLE_LIST_ROUTE
import com.example.restapipractice.USER_LIST_ROUTE
import com.example.restapipractice.USER_SAVED_ROUTE
import com.example.restapipractice.ui.news.ArticleListScreen
import com.example.restapipractice.ui.news.ArticleViewModel
import com.example.restapipractice.ui.user.UserListScreen
import com.example.restapipractice.ui.user.UserSavedScreen
import com.example.restapipractice.ui.user.UserViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    articleViewModel: ArticleViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = USER_LIST_ROUTE,
        modifier = modifier,
    ) {
        composable(route = USER_LIST_ROUTE,) {
            val users = userViewModel.userList
            UserListScreen(
                userViewModel = userViewModel,
//                users = userViewModel.userList,
            )
        }

        composable(route = USER_SAVED_ROUTE) {
            UserSavedScreen(
                userViewModel = userViewModel,
            )
        }

        composable(route = ARTICLE_LIST_ROUTE) {
            ArticleListScreen(
                articleViewModel = articleViewModel,
            )
        }

    }
}