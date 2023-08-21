import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.briefing.R
import com.dev.briefing.data.News
import com.dev.briefing.ui.theme.GradientEnd
import com.dev.briefing.ui.theme.GradientStart
import com.dev.briefing.ui.theme.MainPrimary
import com.dev.briefing.ui.theme.ShadowColor
import com.dev.briefing.ui.theme.White
import com.dev.briefing.ui.theme.utils.drawColoredShadow
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Preview
@Composable
fun BriefingHome(
    modifier: Modifier = Modifier
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientEnd),
        startY = 50.0f,
        endY = 100.0f
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(brush = gradientBrush),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var horizontalscrollState = rememberScrollState()
        val timeList:MutableList<String> = mutableListOf("fdfd","fdfsf")
        HomeHeader()
        Spacer(modifier = Modifier.height(29.dp))
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .scrollable(horizontalscrollState, Orientation.Horizontal),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(timeList) { time ->
                Text(
                    time, style =  MaterialTheme.typography.headlineLarge.copy(color = White)
                )
            }
        }
        Text(
           "2023년 8월 7일",
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "오늘의 키워드 브리핑",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(29.dp))
        ArticleList()
        }

    }


@Preview
@Composable
fun HomeHeader(
    modifier: Modifier = Modifier
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 27.dp, vertical = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,

    ){
        Text(text =  stringResource(R.string.home_title),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight(400)
            ))

        Row {
            Image(
                painter = painterResource(
                    id = R.drawable.storage
                ),
                contentDescription = "저장 공간"
            )
            Spacer(modifier = Modifier.width(22.dp))
            Image(painter = painterResource(
                id = R.drawable.setting),
                contentDescription = "저장 공간")
        }

    }
}

@Composable
fun ArticleList(
modifier: Modifier = Modifier
){
    var newsList:List<News> = listOf(
        News(1,"잼버리","test1"),
        News(2,"잼버리","test1")
    )
    Column(
        modifier.background(White)
            .fillMaxHeight()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 11.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Korea - Global Switch 때체
            Image(painter =painterResource(id = R.drawable.setting) , contentDescription = "fdfd")
            Text(
                text = "R.drawable.setting",
                style = MaterialTheme.typography.labelMedium
            )

        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            items(newsList){it ->
                ArticleListTile(it,modifier)
            }
        }
    }
}
@Composable
fun ArticleListTile(
    news: News = News(1,"잼버리","test1"),
    modifier: Modifier = Modifier
){
    Row(
        modifier.fillMaxWidth()
            .clip(RoundedCornerShape(40.dp))
            .background(White)

            .drawColoredShadow(
            color = ShadowColor,
            offsetX = 0.dp,
            offsetY = 4.dp,
            alpha = 0.1f,
            shadowRadius = 4.dp,
            borderRadius = 10.dp)

        .padding(vertical = 15.dp, horizontal = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Modifier
            .padding(1.dp)
            .width(45.dp)
            .height(45.dp)
            .background(color = MainPrimary)
        Column() {
            Text(text = news.title,style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = news.subtitle,style = MaterialTheme.typography.labelSmall)
        }
        Image(painter =painterResource(id = R.drawable.left_arrow) , contentDescription = "fdfd")
    }
}

