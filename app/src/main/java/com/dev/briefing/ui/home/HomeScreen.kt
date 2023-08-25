import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dev.briefing.R
import com.dev.briefing.data.News
import com.dev.briefing.navigation.HomeScreen
import com.dev.briefing.ui.article.ArticleScreen
import com.dev.briefing.ui.theme.*
import com.dev.briefing.ui.theme.utils.drawColoredShadow
import com.dev.briefing.util.UPDATE_DATE
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun BriefingHome(
    modifier: Modifier = Modifier,
    onScrapClick: () -> Unit,
    onSettingClick: () -> Unit,
    navController: NavController
//    onDetailClick:(Int) -> Unit
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientEnd),
        startY = 0.0f,
        endY = LocalConfiguration.current.screenHeightDp.toFloat()
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(brush = gradientBrush),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //scroll
        var horizontalscrollState = rememberScrollState()

        //time관련 변수
        val timeList: MutableList<LocalDate> = mutableListOf()
        var updateDate: LocalDate = UPDATE_DATE
        val today: LocalDate = LocalDate.now()
        //TODO: time add start 출시일 ~ end 오늘날
        while (today.isAfter(updateDate)) {
            timeList.add(updateDate)
            Log.d("time", updateDate.format(DateTimeFormatter.ofPattern("yy.MM.dd")))
            updateDate = updateDate.plusDays(1)
        }
        timeList.add(today)
        Log.d("time", timeList.size.toString())

        var briefDate by remember { mutableStateOf(today) }
        var briefText = if (briefDate == today) {
            "오늘"
        } else {
            "그날"
        }

        HomeHeader(
            onScrapClick = onScrapClick,
            onSettingClick = onSettingClick
        )

        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .scrollable(horizontalscrollState, Orientation.Horizontal),
            horizontalArrangement = Arrangement.spacedBy(17.dp)
        ) {
            items(timeList) { time ->
                Text(
                    modifier = Modifier.clickable {
                        briefDate = time
                    },
                    text = time.format(DateTimeFormatter.ofPattern("yy.MM.dd")),
                    textDecoration = if (briefDate == time) TextDecoration.Underline else TextDecoration.None,
                    style = MaterialTheme.typography.headlineLarge.copy(color = White)
                )
            }
        }

        Spacer(modifier = Modifier.height(29.dp))
        Text(
            "${briefDate.year}년 ${briefDate.month.value}월 ${briefDate.dayOfMonth}일",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = White
            )
        )
        Text(
            text = "${briefText}의 키워드 브리핑",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(29.dp))

        ArticleList(
            navController = navController
        )
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
            .padding(horizontal = 27.dp, vertical = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Text(
            text = stringResource(R.string.home_title),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight(400)
            )
        )

        Row {
            Image(
                painter = painterResource(
                    id = R.drawable.storage
                ),
                contentDescription = "저장 공간", modifier = Modifier
                    .clickable(onClick = onScrapClick)
            )
            Spacer(modifier = Modifier.width(22.dp))
            Image(
                painter = painterResource(
                    id = R.drawable.setting
                ),
                contentDescription = "설정", modifier = Modifier
                    .clickable(onClick = onSettingClick)
            )
        }

    }
}

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
//    onDetailClick: (Int)->Unit,
    navController: NavController
) {
    var newsList: List<News> = listOf(
        News(1, 1, "잼버리", "test1"),
        News(2, 2, "잼버리", "test1"),
        News(3, 3, "잼버리", "test1"),
        News(4, 4, "잼버리", "test1"),
        News(51, 5, "잼버리", "test1"),
        News(1, 6, "잼버리", "test1"),
    )
    Column(
        modifier.background(SubBackGround, shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .fillMaxHeight()
            .padding(horizontal = 17.dp)

    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 11.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Korea - Global Switch 때체
            Image(painter = painterResource(id = R.drawable.setting), contentDescription = "fdfd")
            Text(
                text = "Updated: 23.08.07 5AM",
                style = MaterialTheme.typography.labelMedium
            )

        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            items(newsList) { it ->
                ArticleListTile(news = it, onItemClick = { id ->
                    navController.navigate("${HomeScreen.Detail.route}/$id")
                    Log.d("2", id.toString())
                })
            }
        }
    }
}

@Composable
fun ArticleListTile(
    news: News = News(1, 1, "잼버리", "test1"),
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    Row(
        modifier.fillMaxWidth()
            .clickable {
                Log.d("1", news.id.toString())
                onItemClick(news.id)
            }
            .drawColoredShadow(
                color = ShadowColor,
                offsetX = 0.dp,
                offsetY = 2.dp,
                alpha = 0.1f,
//                shadowRadius = 4.dp,
                borderRadius = 5.dp
            )
            .background(White, shape = RoundedCornerShape(40.dp))

            .padding(vertical = 15.dp, horizontal = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            val backgroundColor = when (news.rank) {
                1 -> MainPrimary
                2 -> MainPrimary2
                3 -> MainPrimary3
                else -> White
            }
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(color = backgroundColor,shape = RoundedCornerShape(50.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = news.rank.toString(), style = MaterialTheme.typography.titleSmall.copy(
                    color = if(backgroundColor==White) MainPrimary else White
                ))

            }
            Column() {
                Text(text = news.title, style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = news.subtitle, style = MaterialTheme.typography.labelSmall)
            }


        }


        Image(painter = painterResource(id = R.drawable.left_arrow), contentDescription = "fdfd")
    }
}


