package com.dev.briefing.presentation.scrap

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dev.briefing.navigation.HomeScreen
import com.dev.briefing.presentation.theme.component.CommonHeader
import com.dev.briefing.R
import com.dev.briefing.data.News
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.presentation.theme.BriefingTheme
import org.koin.androidx.compose.getViewModel

@Preview
@Composable
fun PreviewScrapScreen() {
    ScrapScreen(
        onBackClick = {},
        navController = rememberNavController()
    )
}

@Composable
fun ScrapScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: ScrapViewModel = getViewModel<ScrapViewModel>()
    val scrapMap = viewModel.scrapMap.observeAsState()
    Column {
        CommonHeader(header = "북마크한 브리핑", onBackClick = onBackClick)

        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = BriefingTheme.color.BackgroundWhite),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

//            if (scrapMap.value.isNullOrEmpty()) {
//                ScrapDefaultScreen()
//            } else {
            LazyColumn(
                modifier = Modifier.padding(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(35.dp)
            ) {
                items(2) {
                    ScrapItem(news = ScrapResponse(
                        briefingId = 1,
                        title = "title",
                        subtitle = "subtitle",
                        ranks = 1,
                        date = "2023-1-1"
                    ), onItemClick = {})
                }

            }
//            }
        }
    }

}

/**
 * 스크랩이 없을 때 보여줄 화면
 */
@Preview
@Composable
fun ScrapDefaultScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_scrap_default),
            contentDescription = "alert"
        )
        Spacer(modifier = Modifier.height(33.dp))
        Text(
            text = stringResource(id = R.string.scrap_default),
            style = BriefingTheme.typography.SubtitleStyleBold.copy(color = BriefingTheme.color.TextGray)
        )
    }
}

/**
 * 스크랩화면 전체적인 틀
 * 1.1에서는 날짜별로 묶어서 보여주는 Section이 있었기에
 * 해당 Layout을 분리
 */
@Composable
fun ArticleSection(
    modifier: Modifier = Modifier,
    localDate: String,
    tmpNewsList: List<ScrapResponse>,
    navController: NavController
) {
    val newsList = tmpNewsList.sortedBy { it.ranks }
    Column(
        modifier = modifier
            .fillMaxWidth()
//            .padding(horizontal = 24.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = localDate,
            style = BriefingTheme.typography.TitleStyleBold
        )
        Spacer(modifier = Modifier.height(6.dp))

        Column(
            modifier = Modifier.background(
                color = BriefingTheme.color.BackgroundWhite,
                shape = RoundedCornerShape(5.dp)
            )

        ) {
            newsList.forEach { news ->
                ScrapItem(news = news, onItemClick = { id ->
                    navController.navigate("${HomeScreen.Detail.route}/$id")
                    Log.d("2", id.toString())
                })
            }
        }
    }
}

@Preview
@Composable
fun PreviewScrapItem() {
    ScrapItem(
        news = ScrapResponse(
            briefingId = 1,
            title = "title",
            subtitle = "subtitle",
            ranks = 1,
            date = "2023-1-1"
        ),
        onItemClick = {}
    )
}

/**
 * Scrap item
 * click시 상세페이지로 이동
 */
@Composable
fun ScrapItem(
    modifier: Modifier = Modifier,
    news: ScrapResponse,
    onItemClick: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 23.dp, vertical = 13.dp)
            .clickable {
                onItemClick(news.briefingId)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "${news.title} | 이슈 #${news.title} | ${news.title}로 생성됨",
            style = BriefingTheme.typography.DetailStyleRegular.copy(
                color = BriefingTheme.color.TextGray
            )
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = news.title,
            style = BriefingTheme.typography.SubtitleStyleBold,
        )
        Text(
            text = news.subtitle,
            style = BriefingTheme.typography.ContextStyleRegular25.copy(
                color = BriefingTheme.color.TextGray,
            )
        )
    }


}