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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.NavController
import com.dev.briefing.R
import com.dev.briefing.data.News
import com.dev.briefing.data.model.BriefingPreview
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.navigation.HomeScreen
import com.dev.briefing.presentation.home.HomeViewModel
import com.dev.briefing.presentation.theme.*
import com.dev.briefing.util.SERVER_TAG
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.koin.androidx.compose.getViewModel

@Composable
fun BriefingHome(
    modifier: Modifier = Modifier,
    onScrapClick: () -> Unit,
    onSettingClick: () -> Unit,
    navController: NavController,
//    onDetailClick:(Int) -> Unit
) {

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientEnd),
        startY = 0.0f,
        endY = LocalConfiguration.current.screenHeightDp.toFloat()
    )

    val homeViewModel: HomeViewModel = getViewModel<HomeViewModel>()
    val briefingResponseState = homeViewModel.serverTestResponse.observeAsState(
        initial = BriefingResponse(
            created_at = "2023-08-27",
            briefings = listOf(BriefingPreview(1, 1, "잼버리", "test1"))
        )
    )
//    val briefingResponseFlow by remember { mutableStateOf( homeViewModel.serverTestResponse) }
    Log.d(SERVER_TAG, "화면에 ${briefingResponseState.value.briefings}")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(brush = gradientBrush),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        //scroll
        var horizontalscrollState = rememberScrollState()


        HomeHeader(
            onScrapClick = onScrapClick,
            onSettingClick = onSettingClick
        )
        LaunchedEffect(Unit) {
            horizontalscrollState.animateScrollTo(Int.MAX_VALUE)
            Log.d("되니","되니")
        }
//
        LazyRow(
            modifier = modifier
                .scrollable(horizontalscrollState, Orientation.Horizontal)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(17.dp)
        ) {
            items(homeViewModel.timeList) { time ->
                val clickAble = time != LocalDate.of(2022,11,22)
                val color = if(!clickAble) Color.Transparent else White

                Text(
                    modifier = Modifier.clickable(clickAble) {
                            homeViewModel.changeBriefDate(time)
                    },
                    text = time.format(DateTimeFormatter.ofPattern("yy.MM.dd")),
                    textDecoration = if (homeViewModel.briefDate.value == time) TextDecoration.Underline else TextDecoration.None,
                    style = MaterialTheme.typography.headlineLarge.copy(color = color)
                )
            }
        }

        Spacer(modifier = Modifier.height(29.dp))
        Text(
            "${homeViewModel.briefDate.value?.year}년 ${homeViewModel.briefDate.value?.monthValue}월 ${homeViewModel.briefDate.value?.dayOfMonth}일",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = White
            )
        )
        Text(
            text = "${homeViewModel.briefText}의 키워드 브리핑",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(29.dp))

        ArticleList(
            navController = navController,
            briefingResponse = briefingResponseState.value
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
    briefingResponse: BriefingResponse,
    navController: NavController
) {
    Column(
        modifier.background(SubBackGround, shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .fillMaxHeight()
            .padding(horizontal = 17.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 11.dp),
            horizontalArrangement = Arrangement.End
        ) {
            //Korea - Global Switch 때체
            Text(
                text = "Updated: ${briefingResponse.created_at} 5AM",
                style = MaterialTheme.typography.labelMedium
            )

        }
        Log.d(SERVER_TAG, briefingResponse.briefings?.size.toString())
        if (briefingResponse.briefings?.isEmpty() == true) {
            Text(
                modifier = modifier.align(Alignment.CenterHorizontally),
                text = "컨텐츠 준비중입니다",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MainPrimary
                ),
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                items(
                    briefingResponse.briefings ?: listOf(
                        BriefingPreview(1, 1, "잼버리", "test1"),
                    )
                ) { it ->
                    ArticleListTile(news = it, onItemClick = { id ->
                        navController.navigate("${HomeScreen.Detail.route}/$id")
                        Log.d("2", id.toString())
                    })
                }
            }
        }


    }
}

@Composable
fun ArticleListTile(
    news: BriefingPreview,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    Row(
        Modifier.shadow(elevation = 20.dp, spotColor = Color(0x1A000000), ambientColor = Color(0x1A000000))
            .fillMaxWidth()
            .clickable {
                onItemClick(news.id)
            }
            .background(White, shape = RoundedCornerShape(40.dp))
            .padding(vertical = 15.dp, horizontal = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
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
                    .background(color = backgroundColor, shape = RoundedCornerShape(50.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = news.rank.toString(), style = MaterialTheme.typography.titleSmall.copy(
                        color = if (backgroundColor == White) MainPrimary else White
                    )
                )

            }
            Column(
                modifier = modifier.fillMaxHeight().widthIn(max = 185.dp),
                verticalArrangement = Arrangement.Center,

            ) {
                Text(text = news.title, style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = news.subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }


        Image(
            modifier = Modifier.width(27.dp)
                .height(27.dp),
            painter = painterResource(id = R.drawable.left_arrow), contentDescription = "fdfd"
        )
    }
}


