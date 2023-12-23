package com.dev.briefing.presentation.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dev.briefing.R
import com.dev.briefing.model.RelatedArticle
import com.dev.briefing.presentation.theme.BriefingTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun ArticleDetailScreen(
    articleDetailViewModel: ArticleDetailViewModel = getViewModel(),
    articleId: Long,
    onBackClick: () -> Unit = {},
    navController: NavController = rememberNavController()
) {
    LaunchedEffect(articleId) {
        articleDetailViewModel.loadBriefingArticle(articleId)
    }

    val _uiState =
        articleDetailViewModel.briefingArticleState.collectAsStateWithLifecycle(ArticleDetailUiState.Loading)

    Column(
        Modifier
            .fillMaxSize()
            .background(color = BriefingTheme.color.BackgroundWhite)
    ) {

        when (val uiState = _uiState.value) {
            is ArticleDetailUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            is ArticleDetailUiState.Error -> {
            }

            is ArticleDetailUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    val article = uiState.article
                    TopBar(onBackPressed = onBackClick)

                    ArticleDetailHeader(
                        modifier = Modifier.padding(30.dp, 18.dp),
                        title = article.title,
                        date = article.createdDate.toString(),
                        section = article.category.typeName,
                        generatedEngine = article.gptModel,
                        scrapCount = article.scrapCount,
                        isScrapingInProgress = uiState.isScrapingInProgress,
                        isScrapped = article.isScrap,
                        onScrapClick = {
                            if (article.isScrap) {
                                articleDetailViewModel.unScrap(article.id)
                            } else {
                                articleDetailViewModel.setScrap(article.id)
                            }
                        }
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(20.dp, 0.dp)
                            .fillMaxWidth()
                            .height(0.5.dp)
                            .background(color = Color(0xFFDADADA))
                    )

                    ArticleSummary(
                        modifier = Modifier.padding(30.dp, 20.dp),
                        summaryTitle = article.subtitle,
                        summaryContent = article.content
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(20.dp, 0.dp)
                            .fillMaxWidth()
                            .height(0.5.dp)
                            .background(color = Color(0xFFDADADA))
                    )

                    RelatedArticles(Modifier.padding(32.dp, 16.dp), article.relatedArticles)
                }
            }
        }
    }
}


@Composable
@Preview
fun ArticleDetailScreenPreview() {
    BriefingTheme {
        ArticleDetailScreen(articleId = 0)
    }
}

@Composable
fun RelatedArticles(modifier: Modifier = Modifier, relatedArticles: List<RelatedArticle>) {
    val context = LocalContext.current
    Column(modifier) {
        Text(
            text = "관련 기사",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF000000)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        relatedArticles.forEachIndexed { index, article ->
            RelatedArticle(
                article.press,
                article.title
            ) {
                val webPage: Uri = Uri.parse(article.url)
                val intent = Intent(Intent.ACTION_VIEW, webPage)
                startActivity(context, intent, null)
            }

            if (index < relatedArticles.size - 1) {
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

//
//        LazyColumn(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
//            items(relatedArticles) {
//                RelatedArticle(
//                    it.press,
//                    it.title
//                )
//            }
//        }
    }
}

@Preview
@Composable
fun RelatedArticlePreview() {
    BriefingTheme {
        RelatedArticle(LoremIpsum(3).values.joinToString(), LoremIpsum(4).values.joinToString()) {

        }
    }
}

@Composable
fun RelatedArticle(title: String, description: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black)
            .clickable {
                onClick.invoke()
            }
            .padding(16.dp, 10.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000)
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                )
            )
        }

        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.ic_left_arrow_slim),
                contentDescription = null
            )
        }

    }
}

@Preview
@Composable
fun ArticleSummaryPreview() {
    BriefingTheme {
        ArticleSummary(
            Modifier.fillMaxWidth(),
            LoremIpsum(3).values.joinToString(),
            LoremIpsum(20).values.joinToString()
        )
    }
}

@Composable
fun ArticleSummary(
    modifier: Modifier = Modifier,
    summaryTitle: String,
    summaryContent: String
) {
    Column(modifier) {
        Text(
            text = summaryTitle,
            style = BriefingTheme.typography.TitleStyleBold.merge(
                fontSize = 20.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = summaryContent,
            style = BriefingTheme.typography.DetailStyleRegular.merge(
                fontSize = 17.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
            )
        )
    }
}

@Preview
@Composable
fun ArticleHeaderPreview() {
    BriefingTheme {
        ArticleDetailHeader(
            title = LoremIpsum(3).values.joinToString(),
            date = "1970.01.01 아침",
            section = "사회 #1",
            generatedEngine = "GPT-3로 생성됨",
            scrapCount = 763,
            isScrapped = true,
            onScrapClick = {}
        )
    }
}

@Composable
fun ArticleDetailHeader(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    section: String,
    generatedEngine: String,
    scrapCount: Int,
    isScrapped: Boolean = false,
    isScrapingInProgress: Boolean = false,
    onScrapClick: () -> Unit
) {
    Row(modifier) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                style = BriefingTheme.typography.TitleStyleBold.copy(
                    color = Color.Black
                ), text = title
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF7C7C7C),
                ), text = "$date | $section | $generatedEngine"
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = scrapCount.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF7C7C7C)
                )
            )

            val iconColor = animateColorAsState(
                targetValue = when {
                    isScrapingInProgress -> BriefingTheme.color.TextGray
                    isScrapped -> BriefingTheme.color.PrimaryBlue
                    else -> BriefingTheme.color.TextGray
                }, label = ""
            )

            IconButton(onClick = { if (!isScrapingInProgress) onScrapClick.invoke() }) {
                Icon(
                    painter = if (isScrapped || isScrapingInProgress) painterResource(id = R.drawable.bookmark_enable) else painterResource(
                        id = R.drawable.bookmark_breifingcard
                    ),
                    tint = iconColor.value,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun TopBar(onBackPressed: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(12.dp, 4.dp)
    ) {

        IconButton(onClick = onBackPressed, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(
                modifier = Modifier.size(16.dp, 16.dp),
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = null
            )
        }

        IconButton(onClick = onBackPressed, modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                contentDescription = null
            )
        }
    }
}