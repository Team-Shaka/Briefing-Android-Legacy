import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dev.briefing.R
import com.dev.briefing.data.model.BriefingPreview
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.navigation.HomeScreen
import com.dev.briefing.presentation.common.BriefingTabRow
import com.dev.briefing.presentation.home.HomeCategory
import com.dev.briefing.presentation.home.HomeViewModel
import com.dev.briefing.presentation.theme.*
import com.dev.briefing.util.MEMBER_ID
import com.dev.briefing.util.MainApplication.Companion.prefs
import com.dev.briefing.util.SERVER_TAG
import java.time.format.DateTimeFormatter
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate

@Preview
@Composable
fun BriefingHomeScreenPreview() {
    val navController = rememberNavController()

    BriefingTheme {
        BriefingHomeScreen(onSettingClick = { }, navController = navController) {

        }
    }
}

@Composable
fun BriefingHomeScreen(
    modifier: Modifier = Modifier,
    onSettingClick: () -> Unit,
    navController: NavController,
    onBackClick: () -> Unit,
) {
    var selectedTabIdx by remember {
        mutableIntStateOf(0)
    }

    val context = LocalContext.current

    val memberId = prefs.getSharedPreference(MEMBER_ID, -1)
    val isMember = remember { mutableStateOf(memberId != -1) }
    val openAlertDialog = remember { mutableStateOf(false) }

    val homeViewModel: HomeViewModel = getViewModel<HomeViewModel>()
    val briefingResponseState = homeViewModel.serverTestResponse.observeAsState(
        initial = BriefingResponse(
            created_at = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
            briefings = listOf()
        )
    )

    Column(
        Modifier
            .fillMaxSize()
            .background(color = BriefingTheme.color.BackgroundWhite)
    ) {
        HomeHeader(
            onScrapClick = {
                Log.d(SERVER_TAG, "스크랩 클릭 멤버여부: ${isMember.value} 오픈여부: $openAlertDialog.value")
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

        BriefingTabRow(
            tabs = HomeCategory.values().map {
                stringResource(id = it.tabTitle)
            },
            selectedTabIndex = selectedTabIdx,
            onTabSelected = {
                selectedTabIdx = it
            })

        CategoryArticleList(createdAt = "2023.11.02 (목) 아침브리핑", articles = (1..10).map {
            BriefingPreview(
                0,
                it,
                LoremIpsum(3).values.joinToString(),
                LoremIpsum(10).values.joinToString()
            )
        }, onArticleSelect = {
            navController.navigate(HomeScreen.BriefingCard.route)
        }, onRefresh = {

        })
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
            style = androidx.compose.ui.text.TextStyle(
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
    articles: List<BriefingPreview>,
    onArticleSelect: (BriefingPreview) -> Unit,
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
            news = BriefingPreview(
                0,
                0,
                LoremIpsum(3).values.joinToString(),
                LoremIpsum(10).values.joinToString()
            ),
            onItemClick = {

            })
    }
}

@Composable
fun ArticleListItem(
    news: BriefingPreview,
    onItemClick: (BriefingPreview) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(news)
            }
            .padding(14.dp)
    ) {
        Text(
            modifier = Modifier.width(54.dp),
            text = "${news.rank}.",
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
            Text(text = news.title, style = BriefingTheme.typography.SubtitleStyleBold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = news.subtitle, style = BriefingTheme.typography.ContextStyleRegular25.copy(
                    color = BriefingTheme.color.TextGray
                )
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
                        text = "+1K",
                        style = BriefingTheme.typography.DetailStyleRegular.copy(color = BriefingTheme.color.TextGray)
                    )
                }
            }
        }
    }
}