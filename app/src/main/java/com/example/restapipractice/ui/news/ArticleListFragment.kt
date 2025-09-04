package com.example.restapipractice.ui.news

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(
    articleViewModel: ArticleViewModel,
    modifier: Modifier = Modifier,
) {
    val default = "점심추천"
    LaunchedEffect(Unit) {
        articleViewModel.fetchArticles(default, 3, 1, "sim")
    }
    val articles = articleViewModel.articleList
    val context = LocalContext.current
    val searchQuery = remember { mutableStateOf("") }
    val options = mapOf(
        "display" to listOf(3, 5, 10),
        "start" to listOf(1, 2, 3),
        "sort" to listOf("sim", "date")
    )
    val selectedDropdownIndex = remember { mutableStateOf(List(options.size) { false }) }
    val optionsSelected = remember { mutableStateMapOf<String, Any>(
        "display" to 3,
        "start" to 1,
        "sort" to "sim"
    ) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Article List") }) },
        content = { innerPadding ->
            Column {

                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = {
                        searchQuery.value = it
                    },
                    label = { Text("검색어 입력") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy( // Added keyboardOptions
                        imeAction = ImeAction.Done // Set Enter key action to "Done"
                    ),
                    keyboardActions = KeyboardActions( // Added keyboardActions
                        onDone = { // Handle "Done" action (Enter key press)
                            // 여기에 엔터키 입력 시 수행할 작업을 정의합니다.
                            articleViewModel.fetchArticles(searchQuery.value, 3, 1, "sim", default)
                        }
                    ),
                    modifier = Modifier
//                        .weight(3f)
//                        .fillMaxHeight()
                        .padding(innerPadding)
                )
                Spacer(modifier = Modifier.padding(1.dp))

                Row {
                    for ((key, value) in options) {
                        Box() {
                            Button(onClick = {
                                selectedDropdownIndex.value =
                                    selectedDropdownIndex.value.mapIndexed { index, isSelected ->
                                        if (index == options.keys.indexOf(key)) !isSelected else isSelected

                                    }
                            },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(key)
                            }

                            DropdownMenu(
                                expanded = selectedDropdownIndex.value[options.keys.indexOf(key)],
                                onDismissRequest = {
                                    selectedDropdownIndex.value = MutableList(options.size) { false }
                                }
                            ) {
                                value.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(item.toString()) },
                                        onClick = {
                                            optionsSelected[key] = item
                                            articleViewModel.fetchArticles(
                                                searchQuery.value,
                                                optionsSelected["display"].toString().toInt(),
                                                optionsSelected["start"].toString().toInt(),
                                                optionsSelected["sort"].toString(),
                                                default.toString(),
                                            )

                                            selectedDropdownIndex.value = List(options.size) { false }
                                        }
                                    )
                                }
                            }
                        }

                    }
                }


                LazyColumn(
//                    modifier = Modifier.padding(innerPadding)
                ) {
                    articles.value.items.forEach {
                        item {
                            Card(
                                modifier = Modifier.padding(16.dp),
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, it.link.toUri())
                                    context.startActivity(intent)
                                }
                            ) {
                                Text(it.title, modifier = Modifier.padding(16.dp), color = Color.Blue, maxLines = 1)
                                Spacer(modifier = Modifier.padding(3.dp))
//                            Text(it.link)
                                Text(it.description, modifier = Modifier.padding(16.dp), maxLines = 3)
//                            Text(it.pubDate)
                            }
                        }
                    }
                }
            }
        }
    )
}
