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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.restapipractice.data.remote.api.UserWithDetailsDTO
import kotlinx.coroutines.launch

// UserViewModel에서 UI 상태를 나타내는 클래스/Sealed Interface를 사용하는 것이 좋습니다.
// 예: data class SavedUserListUiState(val isLoading: Boolean = false, val users: List<UserWithDetails> = emptyList(), val error: String? = null)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSavedScreen(
    userViewModel: UserViewModel,
    onUserClick: (UserWithDetailsDTO) -> Unit, // 상세 화면 이동 등을 위한 콜백
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    // ViewModel에서 StateFlow<List<UserWithDetails>>를 받아오는 것을 권장
    val usersState = remember { mutableStateListOf<UserWithDetailsDTO>() }
    LaunchedEffect(Unit) {
        scope.launch {
            userViewModel.getUsersWithDetailDTO().collect {
                usersState.clear()
                usersState.addAll(it)
            }
        }
    }
    // val isLoading by userViewModel.isSavedUsersLoading.collectAsState(initial = false) // 로딩 상태를 위한 StateFlow가 있다고 가정

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Users") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // if (isLoading && usersState.isEmpty()) {
            //    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            // } else
            if (usersState.isEmpty()) {
                Text(
                    text = "No saved users found.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = usersState,
                        key = { userWithDetails -> userWithDetails.id } // UserWithDetails 내 User 객체의 id 사용
                    ) { userWithDetails ->
                        SavedUserItem(
                            userWithDetails = userWithDetails,
                            onItemClick = { onUserClick(userWithDetails) },
                            onDeleteClick = {
                                scope.launch {
                                    // UserWithDetails 객체 전체를 넘기거나, user.id만 넘겨서 ViewModel에서 처리하도록 변경
                                    // 여기서는 user.id를 넘겨서 ViewModel에서 관련된 모든 데이터를 삭제하도록 하는 것을 권장
                                    userViewModel.deleteUser(userWithDetails)
                                    usersState.remove(userWithDetails)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SavedUserItem(
    userWithDetails: UserWithDetailsDTO, // Room 엔티티 UserWithDetails
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = userWithDetails.name ?: "N/A",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "@${userWithDetails.username ?: "N/A"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(12.dp))

                userWithDetails.email?.let { UserInfoRow(icon = Icons.Default.Email, text = it) }
                userWithDetails.phone?.let { UserInfoRow(icon = Icons.Default.Phone, text = it) }
                userWithDetails.address?.city?.let { UserInfoRow(icon = Icons.Default.LocationOn, text = it) }
                userWithDetails.company?.name?.let { UserInfoRow(icon = Icons.AutoMirrored.Filled.ArrowBack, text = it) }
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete User",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

// UserInfoRow Composable은 UserListFragment.kt에 정의된 것을 가져오거나, 공통 파일로 분리하여 사용합니다.
// 만약 UserListFragment.kt 와 UserSavedScreen.kt만 UserInfoRow를 사용한다면,
// UserListFragment.kt에 있는 것을 public으로 두고 여기서 import 하거나,
// ui/common 같은 패키지에 공통 Composable로 만들 수 있습니다.
// 여기서는 UserListFragment.kt의 것을 import 해서 사용한다고 가정합니다.
// import com.example.restapipractice.ui.user.UserInfoRow // 실제로는 이 import가 필요할 수 있음
