package com.example.restapipractice.ui.news

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.restapipractice.data.local.entry.News.Article
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(
    articleViewModel: ArticleViewModel,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        articleViewModel.fetchArticles()
    }
    val articles = articleViewModel.articleList
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar(title = { Text("Article List") }) },
        content = { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                articles.value.items.forEach {
                        item {
                        Card(
                            modifier = Modifier.padding(16.dp),
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, it.link.toUri())
                                context.startActivity(intent)
                            }
                        ) {
                            Text(it.title, modifier = Modifier.padding(16.dp), color = Color.Blue)
                            Spacer(modifier = Modifier.padding(8.dp))
//                            Text(it.link)
                            Text(it.description, modifier = Modifier.padding(16.dp), maxLines = 3)
//                            Text(it.pubDate)
                        }
                    }
                }
            }
        }
    )
}