package com.dev.briefing.ui.article

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.briefing.R
import com.dev.briefing.data.NewsLink
import com.dev.briefing.ui.theme.GradientEnd
import com.dev.briefing.ui.theme.GradientStart
import com.dev.briefing.ui.theme.MainPrimary
import com.dev.briefing.ui.theme.SubText2
import com.dev.briefing.ui.theme.White

@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientEnd),
        startY = 0.0f,
        endY = LocalConfiguration.current.screenHeightDp.toFloat()
    )
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(brush = gradientBrush)
            .padding(horizontal = 30.dp)

    ) {
        DetailHeader(
            onBackClick = onBackClick,
            header = "Briefing #1"
        )
        Spacer(modifier = Modifier.height(34.dp))
        LazyColumn {
            item {
                ArticleDetail()
                Spacer(modifier = Modifier.height(25.dp))
            }
        }
    }
}

@Composable
fun DetailHeader(
    onBackClick: () -> Unit,
    header: String = ""
) {
    var isScrap by remember { mutableStateOf(false) }
    // 이미지 리소스를 불러옵니다.
    val scrap = painterResource(id = R.drawable.scrap_normal)
    val selectScrap = painterResource(id = R.drawable.scrap_selected)

    // 클릭 이벤트를 처리합니다.
    val image = if (isScrap) scrap else selectScrap
    val contentDescription = if (isScrap) "Unliked" else "Liked"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            painter = painterResource(
                id = R.drawable.arrow
            ),
            contentDescription = contentDescription, modifier = Modifier
                .clickable(onClick = onBackClick)
        )
        Text(
            text = "Briefing #1",
            style = MaterialTheme.typography.titleMedium.copy(
                color = White,
                fontWeight = FontWeight(400)
            )
        )
        Image(
            //TODO: icon click여부에 따라 asset변경
            painter = image,
            contentDescription = contentDescription,
            modifier = Modifier.clickable(
                onClick = {
                    isScrap = !isScrap
                    //TODO: 스크랩 icon listener local db 연결
                }
            )
        )

    }
}

@Preview
@Composable
fun ArticleDetail(
    modifier: Modifier = Modifier,
) {
    var newsList: List<NewsLink> = listOf(
        NewsLink("연합뉴스", "잼버리", "test1"),
        NewsLink("연합뉴스", "잼버리", "test1"),
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp),

        ) {

        Spacer(modifier = Modifier.height(11.dp))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "23.08.07 Breifing #1",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = SubText2,
                            fontWeight = FontWeight(400)

                ),
                textAlign = TextAlign.End
            )
        }
        Text(
            text = "잼버리 행사",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "잼버리 행사 관련 논란 및 정부와의 갈등",
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "강원도 새만금에서 열리는 '세계스카우트잼버리' 행사는 영국과 미국 단원의 조기 퇴영과 성범죄 발생 등으로 어려움을 겪고 있습니다. 폭염과 환자 발생으로 프로그램 중단되고, 주최지인 전북 참가자들도 퇴영 결정했습니다. 정부는 수습 총력전을 전개하며 행사 지원에 나서고 있으며, 서울시는 영국 단원들에게 활동 지원을 통해 대회 분위기를 끌어올릴 계획입니다. K팝 콘서트도 연기돼 전주월드컵경기장에서 개최될 예정이며, 국무총리는 대회 안전 관리를 강조하며 대회 정상화를 다짐하고 있습니다.",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight(400),

                )
        )
        Text(
            text = "관련 기사",
            style = MaterialTheme.typography.headlineLarge
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            newsList.forEach { it ->
                ArticleLink(it)
            }
        }
        Spacer(modifier = Modifier.height(25.dp))

    }
}

@Composable
fun ArticleLink(
    newsLink: NewsLink,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .background(White, shape = RoundedCornerShape(40.dp))
            .border(1.dp, MainPrimary, shape = RoundedCornerShape(10.dp))
            .padding(vertical = 9.dp, horizontal = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column() {
            Text(text = newsLink.press, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = newsLink.title, style = MaterialTheme.typography.labelSmall)
        }
        Image(painter = painterResource(id = R.drawable.left_arrow), contentDescription = "fdfd")
    }
}