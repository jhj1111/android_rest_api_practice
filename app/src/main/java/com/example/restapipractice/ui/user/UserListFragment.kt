package com.example.restapipractice.ui.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.restapipractice.data.remote.api.UserWithDetailsDTO
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.io.println

// UserViewModel에서 UI 상태를 나타내는 클래스/Sealed Interface를 사용하는 것이 좋습니다.
// 예: data class UserListUiState(val isLoading: Boolean = false, val users: List<User> = emptyList(), val error: String? = null)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    userViewModel: UserViewModel,
    onUserClick: (UserWithDetailsDTO) -> Unit // 클릭 시 상세 화면 이동 또는 다른 액션 처리용 콜백
) {
//    var usersState: StateFlow<List<UserWithDetailsDTO>>
    val scope = rememberCoroutineScope()
    // ViewModel에서 StateFlow<List<UserWithDetails>>를 받아오는 것을 권장
    val users = remember { mutableStateListOf<UserWithDetailsDTO>() }
    LaunchedEffect(Unit) {
        scope.launch {
            userViewModel.getUsersWithDetailDTO().collect {
                users.clear()
                users.addAll(it)
                if (users.isEmpty()) {
                    val usersState = userViewModel.userList
                    userViewModel.insertUsersWithDetails(usersState.toList())
                }
            }
        }
    }

//    val isLoading by userViewModel.isLoading.observeAsState(initial = false) // isLoading LiveData가 있다고 가정

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("API User List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
//            if (users.isEmpty()) {
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            if (users.isEmpty()) {
                Text(
                    text = "No users found.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = users,
                        key = { user -> user.id } // API 응답 User 객체에 id가 있다고 가정
                    ) { user ->
                        UserItem(user = user, onClick = {
                            // 기존 로직: userViewModel.insertUsersWithDetails(user)
                            // 변경 제안: 상세 화면으로 이동하거나, 명시적 저장 액션 제공
                            onUserClick(user) // 콜백 호출
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: UserWithDetailsDTO, // API 응답 User DTO라고 가정
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = user.name ?: "N/A",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "@${user.username ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(12.dp))

            UserInfoRow(icon = Icons.Default.Email, text = user.email ?: "No email")
            UserInfoRow(icon = Icons.Default.Phone, text = user.phone ?: "No phone")
            UserInfoRow(icon = Icons.Default.LocationOn, text = user.address?.city ?: "No city") // User DTO에 address 객체가 있다고 가정
            user.company?.name?.let { companyName -> // User DTO에 company 객체가 있다고 가정
                UserInfoRow(icon = Icons.Default.AccountCircle, text = companyName)
            }
        }
    }
}

@Composable
fun UserInfoRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 12.dp)
        )
        Text(text = text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
