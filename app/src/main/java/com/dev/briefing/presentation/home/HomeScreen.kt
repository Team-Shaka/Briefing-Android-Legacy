import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.GenericShape

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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.dev.briefing.R
import com.dev.briefing.data.model.BriefingPreview
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.navigation.HomeScreen
import com.dev.briefing.presentation.home.HomeViewModel
import com.dev.briefing.presentation.theme.*
import com.dev.briefing.presentation.theme.utils.CommonDialog
import com.dev.briefing.presentation.theme.utils.alertWidget
import com.dev.briefing.util.SERVER_TAG
import java.time.format.DateTimeFormatter
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate

@Composable
fun BriefingHome(
    modifier: Modifier = Modifier,
    onSettingClick: () -> Unit,
    navController: NavController,
//    onDetailClick:(Int) -> Unit
) {

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientEnd),
        startY = 0.0f,
        endY = LocalConfiguration.current.screenHeightDp.toFloat()
    )
    val openAlertDialog = remember { mutableStateOf(false) }

    val homeViewModel: HomeViewModel = getViewModel<HomeViewModel>()
    val briefingResponseState = homeViewModel.serverTestResponse.observeAsState(
        initial = BriefingResponse(
            created_at = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
            briefings = listOf()
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

        if (openAlertDialog.value) {
            CommonDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    //TODO: navigate login scree
//                    navController.navigate()
                    openAlertDialog.value = false
                },
                dialogTitle = R.string.dialog_login_title,
                dialogText = R.string.dialog_login_text,
                dialogId = R.string.dialog_login_confirm,
                confirmColor = MainPrimary4
            )
        }

        HomeHeader(
            onScrapClick = {
                if (openAlertDialog.value == false) {
                    openAlertDialog.value = true
                } else {
                    navController.navigate(HomeScreen.Scrap.route)
                }
            },
            onSettingClick = onSettingClick
        )

        LazyRow(
            modifier = modifier
                .scrollable(horizontalscrollState, Orientation.Horizontal)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(homeViewModel.timeList) { time ->
                Column(
                    modifier = Modifier
                        .background(
                            color = if (homeViewModel.briefDate.value == time) White else Color.Transparent,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(vertical = 6.dp, horizontal = 10.dp)
                        .clickable {
                            homeViewModel.changeBriefDate(time)
                        },
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //TODO: 요일 앞문자만 대문자로 수정하기
                    Text(
                        text = time.dayOfWeek.name.substring(0, 3),
                        style = Typography.bodyMedium.copy(color = if (homeViewModel.briefDate.value == time) MainPrimary else White)
                    )
                    Text(
                        text = time.dayOfMonth.toString(),
                        style = Typography.titleMedium.copy(color = if (homeViewModel.briefDate.value == time) MainPrimary else White)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(13.dp))

        homeViewModel.briefDate.value?.let {
            ArticleList(
                navController = navController,
                briefingResponse = briefingResponseState.value,
                briefDate = it
            )
        } ?: run {
            ArticleList(
                navController = navController,
                briefingResponse = briefingResponseState.value,
                briefDate = LocalDate.now()
            )
        }
    }

}

private fun getBottomLineShape(bottomLineThickness: Int): Shape {
    return GenericShape { size, _ ->
        // 1) Bottom-left corner
        moveTo(0f, size.height)
        // 2) Bottom-right corner
        lineTo(size.width, size.height)
        // 3) Top-right corner
        lineTo(size.width, size.height - bottomLineThickness)
        // 4) Top-left corner
        lineTo(0f, size.height - bottomLineThickness)
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
    briefDate: LocalDate,
    navController: NavController
) {
    Column(
        modifier
            .background(
                SubBackGround,
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
            )
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 17.dp, vertical = 19.dp)
    ) {
        if (briefingResponse.briefings?.isEmpty() == true) {
            alertWidget()
        } else {
            Text(
                text = "${briefDate.format(DateTimeFormatter.ofPattern("YYYY.MM.dd"))} 키워드 브리핑",
                style = Typography.titleMedium.copy(color = MainPrimary)
            )
            Text(
                text = "Updated: ${briefingResponse.created_at}",
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(13.dp))
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
        Modifier
            .shadow(
                elevation = 20.dp,
                spotColor = Color(0x1A000000),
                ambientColor = Color(0x1A000000)
            )
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
                modifier = modifier
                    .fillMaxHeight()
                    .widthIn(max = 185.dp),
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
            modifier = Modifier
                .width(27.dp)
                .height(27.dp),
            painter = painterResource(id = R.drawable.left_arrow), contentDescription = "fdfd"
        )
    }
}


