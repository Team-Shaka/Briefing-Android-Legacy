package com.dev.briefing.presentation.scrap

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.dev.briefing.data.NewsDetail
import com.dev.briefing.navigation.HomeScreen
import com.dev.briefing.presentation.detail.ArticleDetailScreen
import com.dev.briefing.presentation.setting.CommonHeader
import com.dev.briefing.presentation.theme.MainPrimary3
import com.dev.briefing.presentation.theme.SubBackGround
import com.dev.briefing.presentation.theme.White
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ScrapScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: ScrapViewModel = ScrapViewModel()
    var newsList = viewModel.getScrapData(context)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = SubBackGround)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CommonHeader(header = "스크랩북", onBackClick = onBackClick)


        Spacer(modifier = Modifier.height(50.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(35.dp)
        ) {
//            items(dateList) { it ->
//                ArticleSection(localDate = it,)
//            }
            items(newsList.entries.toList()) { entry ->
                // entry는 Map.Entry<String, List<Int>> 타입입니다.
                ArticleSection(localDate = entry.key, tmpNewsList = entry.value)
            }
        }
    }

}

@Composable
fun ArticleSection(
    modifier: Modifier = Modifier,
    localDate: String,
    tmpNewsList: List<NewsDetail>
) {
    val newsList = tmpNewsList.sortedBy { it.rank }
    Column(
        modifier = modifier
            .fillMaxWidth()
//            .padding(horizontal = 24.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = localDate,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(6.dp))

        Column(
            modifier = Modifier.background(
                color = White,
                shape = RoundedCornerShape(5.dp)
            )

        ) {
            newsList.forEach { news ->
                ArticleHeader(news = news)
            }
        }
    }
}

//TODO: RoomDB만들면서 날짜도 저장
@Composable
fun ArticleHeader(
    modifier: Modifier = Modifier,
    news: NewsDetail,
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 11.dp)
            //TODO: navigate to DetailScreen
//            .clickable(
//                onClick = {
//                     ArticleDetailScreen(
//                        id = news.id
//                    )
//                }
//            ),
        ,verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(

        ) {
            Text(text = news.title, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = news.subtitle, style = MaterialTheme.typography.labelSmall)
        }
        Text(
            text = "${news.subtitle} #" + news.rank, style = MaterialTheme.typography.bodyMedium.copy(
                color = MainPrimary3,
                lineHeight = 15.sp
            )
        )
    }


}