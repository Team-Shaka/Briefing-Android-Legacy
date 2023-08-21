package com.dev.briefing.ui.scrap

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.briefing.R
import com.dev.briefing.data.News
import com.dev.briefing.data.NewsDetail
import com.dev.briefing.ui.theme.MainPrimary3
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ScrapScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.arrow
                ),
                contentDescription = "뒤로가기", modifier = Modifier
                    .clickable(onClick = onBackClick)
            )
            Text(
                text = "스크랩북",
                style = MaterialTheme.typography.titleMedium
            )

        }
        Text(
            text = "Updated: 23.08.07 5AM",
            style = MaterialTheme.typography.labelMedium
        )
        ArticleSection()
    }

}

@Composable
fun ArticleSection(
    modifier: Modifier = Modifier,
) {
    var newsList:List<NewsDetail> = listOf(
        NewsDetail(1,1,"잼버리",LocalDate.of(2023, 8, 22),"fdsfd"),
        NewsDetail(1,1,"잼버리",LocalDate.of(2023, 8, 22),"fdsfd"),
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = LocalDate.of(2023, 8, 22).format(DateTimeFormatter.ofPattern("yy.MM.dd")),
            style = MaterialTheme.typography.headlineLarge
        )
        LazyColumn(
        ) {
            items(newsList){it ->
                ArticleHeader(news = it)
            }
        }
    }

}

//TODO: RoomDB만들면서 날짜도 저장
@Composable
fun ArticleHeader(
    modifier: Modifier = Modifier,
    news: NewsDetail
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 11.dp)
    ) {
        Text(text = news.title, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = news.subtitle, style = MaterialTheme.typography.labelSmall)
    }
    Text(
        text = "23.08.07 #" + news.rank, style = MaterialTheme.typography.bodyMedium.copy(
            color = MainPrimary3,
            lineHeight = 15.sp
        )
    )

}