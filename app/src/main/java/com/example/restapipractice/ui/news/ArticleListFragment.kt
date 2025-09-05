package com.example.restapipractice.ui.news

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.example.restapipractice.data.remote.api.ItemsDto // ArticleDto 내의 items 필드 타입으로 가정
import androidx.core.net.toUri

// ArticleViewModel에서 UI 상태를 나타내는 클래스/Sealed Interface를 사용하는 것이 좋습니다.
// 예: data class ArticleUiState(val isLoading: Boolean = false, val articles: List<ItemsDto> = emptyList(), val query: String = "IT", val error: String? = null)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(
    articleViewModel: ArticleViewModel,
    modifier: Modifier = Modifier,
) {
    val articlesState by articleViewModel.articleList
//    val isLoading by articleViewModel.isLoading.collectAsState() // isLoading StateFlow가 있다고 가정

    var searchQuery by remember { mutableStateOf("IT") } // 초기 검색어
    var displayCount by remember { mutableStateOf(10) }
    var startPosition by remember { mutableStateOf(1) }
    var sortOrder by remember { mutableStateOf("sim") } // sim: 유사도, date: 날짜

    // 초기 데이터 로드
    LaunchedEffect(Unit) {
        articleViewModel.fetchArticles(searchQuery, displayCount, startPosition, sortOrder)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News Articles") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            SearchBarWithOptions(
                searchQuery = searchQuery,
                onQueryChange = { searchQuery = it },
                displayCount = displayCount,
                onDisplayChange = { displayCount = it },
                startPosition = startPosition,
                onStartChange = { startPosition = it },
                sortOrder = sortOrder,
                onSortChange = { sortOrder = it },
                onSearch = {
                    articleViewModel.fetchArticles(searchQuery, displayCount, startPosition, sortOrder)
                }
            )

            Box(modifier = Modifier.weight(1f)) {
//                if (articlesState.items.isEmpty()) {
//                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                if (articlesState.items.isEmpty()) {
                    Text(
                        "No articles found. Try a different search.",
                        modifier = Modifier.align(Alignment.Center).padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    articlesState.items.let { items ->
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(
                                items = items,
                                key = { item -> item.link ?: item.title } // 고유 키 사용
                            ) { articleItem ->
                                NewsArticleItem(article = articleItem)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarWithOptions(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    displayCount: Int,
    onDisplayChange: (Int) -> Unit,
    startPosition: Int, // 이 파라미터는 현재 UI에서 직접 수정하지 않으므로, 필요 없다면 제거 가능
    onStartChange: (Int) -> Unit, // 이 파라미터는 현재 UI에서 직접 수정하지 않으므로, 필요 없다면 제거 가능
    sortOrder: String,
    onSortChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var showOptions by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            label = { Text("Search News") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            trailingIcon = {
                Row {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear Search")
                        }
                    }
                    IconButton(onClick = { showOptions = !showOptions }) {
                        Icon(Icons.Default.AddCircle, contentDescription = "Search Options")
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch()
                focusManager.clearFocus()
            }),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        AnimatedVisibility(visible = showOptions) {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OptionDropdown(
                        label = "Display",
                        selectedValue = displayCount,
                        options = listOf(10, 20, 30, 50),
                        onSelected = onDisplayChange,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OptionDropdown(
                        label = "Sort by",
                        selectedValue = sortOrder,
                        options = listOf("sim", "date"), // sim: relevance, date: recency
                        optionLabels = mapOf("sim" to "Relevance", "date" to "Date"),
                        onSelected = onSortChange,
                        modifier = Modifier.weight(1f)
                    )
                }
                 Spacer(modifier = Modifier.height(8.dp))
                 Button(
                     onClick = {
                         onSearch()
                         focusManager.clearFocus()
                         showOptions = false // Optionally hide options after search
                     },
                     modifier = Modifier.align(Alignment.End)
                 ) {
                     Text("Apply & Search")
                 }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> OptionDropdown(
    label: String,
    selectedValue: T,
    options: List<T>,
    optionLabels: Map<T, String>? = null, // Optional map for display names
    onSelected: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val displayValue = optionLabels?.get(selectedValue) ?: selectedValue.toString()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = displayValue,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor() // 필수: 메뉴를 올바르게 위치시키기 위함
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                val optionDisplayValue = optionLabels?.get(selectionOption) ?: selectionOption.toString()
                DropdownMenuItem(
                    text = { Text(optionDisplayValue) },
                    onClick = {
                        onSelected(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun NewsArticleItem(article: ItemsDto) { // ItemsDto는 API 응답의 개별 기사 항목 DTO
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                article.link.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val title = HtmlCompat.fromHtml(article.title, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            val description = HtmlCompat.fromHtml(article.description, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                // 날짜 형식을 변경하고 싶다면 여기서 처리 (예: API 응답의 pubDate를 파싱하여 보기 좋게)
                text = article.pubDate, 
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}
