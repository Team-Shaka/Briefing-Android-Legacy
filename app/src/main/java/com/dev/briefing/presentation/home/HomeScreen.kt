import android.app.Activity
import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dev.briefing.R
import com.dev.briefing.model.BriefingCompactArticle
import com.dev.briefing.navigation.HomeScreen
import com.dev.briefing.presentation.common.BriefingTabRow
import com.dev.briefing.presentation.home.HomeCategory
import com.dev.briefing.presentation.home.HomeViewModel
import com.dev.briefing.presentation.theme.*
import com.dev.briefing.presentation.theme.utils.CommonDialog
import com.dev.briefing.util.preference.AuthPreferenceHelper
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Preview
@Composable
fun BriefingHomeScreenPreview() {
    val navController = rememberNavController()

    BriefingTheme {
        BriefingHomeScreen(onSettingClick = { }, navController = navController)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BriefingHomeScreen(
    onSettingClick: () -> Unit,
    navController: NavController,
    homeViewModel: HomeViewModel = getViewModel()
) {
    val authPreferenceHelper = AuthPreferenceHelper(LocalContext.current)
    val uiState = homeViewModel.briefingArticleUiState.collectAsStateWithLifecycle()

    val memberId = authPreferenceHelper.getMemberId()
    val isMember = remember { mutableStateOf(memberId != -1) }
    val openAlertDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (!isMember.value && openAlertDialog.value) {
        CommonDialog(
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = {
                openAlertDialog.value = false

                (context as Activity).finish()
            },
            dialogTitle = R.string.dialog_login_title,
            dialogText = R.string.dialog_login_text,
            dialogId = R.string.dialog_login_confirm,
            confirmColor = BriefingTheme.color.PrimaryBlue
        )
    }

    val composeCoroutine = rememberCoroutineScope()

    val state = uiState.value

    Column(
        Modifier
            .fillMaxSize()
            .background(color = BriefingTheme.color.BackgroundWhite)
    ) {
        HomeHeader(
            onScrapClick = {
                if (!openAlertDialog.value) {
                    if (!isMember.value) {
                        openAlertDialog.value = true
                    } else {
                        navController.navigate(HomeScreen.Scrap.route)
                    }
                } else {
                    openAlertDialog.value = false
                }
            }, onSettingClick = onSettingClick
        )

        val pagerState = rememberPagerState(pageCount = { HomeCategory.values().size })
        var selectedTabIdx by remember { mutableIntStateOf(0) }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                selectedTabIdx = page
            }
        }

        BriefingTabRow(
            tabs = HomeCategory.values().map {
                stringResource(id = it.tabTitle)
            },
            selectedTabIndex = selectedTabIdx,
            onTabSelected = {
                composeCoroutine.launch {
                    pagerState.animateScrollToPage(it)
                }
            })

        HorizontalPager(state = pagerState) { page ->
            val briefingArticleCategory = HomeCategory.values()[page].category
            homeViewModel.loadBriefings(HomeCategory.values()[page].category)

            HomeScreenArticleList(
                isFetchRefreshing = state.currentLoadingCategories.contains(
                    briefingArticleCategory
                ), onRefreshRequest = {
                    homeViewModel.loadBriefings(HomeCategory.values()[page].category, true)
                },
                articles = state.briefingArticles.getOrDefault(briefingArticleCategory, listOf()),
                onArticleSelect = {
                    navController.navigate("${HomeScreen.Detail.route}/${it.id.toLong()}")
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenArticleList(
    isFetchRefreshing: Boolean,
    onRefreshRequest: () -> Unit,
    articles: List<BriefingCompactArticle>,
    onArticleSelect: (briefingCompactArticle: BriefingCompactArticle) -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState()
    val scaleFraction = if (pullRefreshState.isRefreshing) 1f else
        LinearOutSlowInEasing.transform(pullRefreshState.progress).coerceIn(0f, 1f)

    // Pull To Refresh 영역 진입 시 onRefereshRequest invoke.
    LaunchedEffect(pullRefreshState.isRefreshing) {
        if (pullRefreshState.isRefreshing && !isFetchRefreshing) {
            onRefreshRequest.invoke()
        }
    }

    LaunchedEffect(key1 = isFetchRefreshing) {
        if (!isFetchRefreshing && pullRefreshState.isRefreshing) {
            pullRefreshState.endRefresh()
        }
        if (isFetchRefreshing && !pullRefreshState.isRefreshing) {
            pullRefreshState.startRefresh()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullRefreshState.nestedScrollConnection)
    ) {
        CategoryArticleList(
            createdAt = "2023.11.02 (목) 아침브리핑",
            articles = articles,
            onArticleSelect = onArticleSelect,
            onRefresh = {
                onRefreshRequest.invoke()
            })

        PullToRefreshContainer(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction),
            state = pullRefreshState
        )
    }
}

@Composable
@Preview
fun ArticleListDatePreview() {
    BriefingTheme {
        ArticleListDate(createdAt = "2023.11.02 (목) 아침브리핑") {

        }
    }
}

@Composable
fun ArticleListDate(createdAt: String, onRefresh: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 8.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = createdAt,
            style = BriefingTheme.typography.ContextStyleRegular25.copy(
                color = BriefingTheme.color.TextGray,
                textAlign = TextAlign.Center
            )
        )

        Box(modifier = Modifier
            .align(Alignment.CenterEnd)
            .border(width = 1.dp, shape = CircleShape, color = BriefingTheme.color.TextGray)
            .size(27.dp)
            .clip(CircleShape)
            .clickable {
                onRefresh.invoke()
            }) {
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = null,
                tint = BriefingTheme.color.TextGray
            )
        }

    }
}

@Preview
@Composable
fun HomeHeaderPreview() {
    BriefingTheme {
        HomeHeader(onScrapClick = { }, onSettingClick = {})
    }
}

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    onScrapClick: () -> Unit,
    onSettingClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 27.dp, vertical = 27.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = stringResource(R.string.home_title),
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = ProductSans,
                fontWeight = FontWeight(700),
                color = BriefingTheme.color.PrimaryBlue,
            )
        )

        IconButton(modifier = Modifier.size(24.dp), onClick = onScrapClick) {
            Icon(
                modifier = Modifier.size(width = 23.dp, height = 19.dp), painter = painterResource(
                    id = R.drawable.storage
                ), tint = BriefingTheme.color.TextBlack, contentDescription = null
            )
        }

        Spacer(Modifier.width(20.dp))

        IconButton(modifier = Modifier.size(24.dp), onClick = onSettingClick) {
            Icon(
                modifier = Modifier.size(24.dp, 24.dp), painter = painterResource(
                    id = R.drawable.setting
                ), tint = BriefingTheme.color.TextBlack, contentDescription = null
            )
        }

    }
}

@Composable
fun CategoryArticleList(
    createdAt: String,
    articles: List<BriefingCompactArticle>,
    onArticleSelect: (BriefingCompactArticle) -> Unit,
    onRefresh: () -> Unit
) {
    LazyColumn {
        item {
            ArticleListDate(createdAt, onRefresh)
            Divider(color = BriefingTheme.color.SeperatorGray)
        }
        items(items = articles) {
            ArticleListItem(it, onArticleSelect)
            Divider(color = BriefingTheme.color.SeperatorGray)
        }
    }
}


@Preview
@Composable
fun CategoryArticleListItemPreview() {
    BriefingTheme {
        ArticleListItem(
            article = BriefingCompactArticle(
                id = 0,
                ranks = 0,
                title = LoremIpsum(3).values.joinToString(),
                subtitle = LoremIpsum(10).values.joinToString(),
                scrapCount = 0
            ),
            onItemClick = {

            })
    }
}

@Composable
fun ArticleListItem(
    article: BriefingCompactArticle,
    onItemClick: (BriefingCompactArticle) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(article)
            }
            .padding(14.dp)
    ) {
        Text(
            modifier = Modifier.width(54.dp),
            text = "${article.ranks}.",
            style = TextStyle(
                fontSize = 35.sp,
                fontFamily = ProductSans,
                fontWeight = FontWeight(700),
                color = BriefingTheme.color.PrimaryBlue,
                textAlign = TextAlign.Right,
            )
        )

        Spacer(Modifier.width(16.dp))

        Column {
            Text(text = article.title, style = BriefingTheme.typography.SubtitleStyleBold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = article.subtitle,
                style = BriefingTheme.typography.ContextStyleRegular25.copy(
                    color = BriefingTheme.color.TextGray
                ),
                minLines = 2,
                maxLines = 2
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.align(Alignment.BottomEnd), verticalAlignment =
                    Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(17.dp),
                        painter = painterResource(id = R.drawable.bookmark),
                        contentDescription = null,
                        tint = BriefingTheme.color.TextGray
                    )
                    Text(
                        text = article.scrapCount.toString(),
                        style = BriefingTheme.typography.DetailStyleRegular.copy(color = BriefingTheme.color.TextGray)
                    )
                }
            }
        }
    }
}