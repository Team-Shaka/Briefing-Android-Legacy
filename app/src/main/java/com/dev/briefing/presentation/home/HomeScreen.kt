import android.app.Activity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import com.dev.briefing.util.JWT_TOKEN
import com.dev.briefing.util.MEMBER_ID
import com.dev.briefing.util.MainApplication.Companion.prefs
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
    val context = LocalContext.current
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientEnd),
        startY = 0.0f,
        endY = LocalConfiguration.current.screenHeightDp.toFloat()
    )
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
    val briefDate = homeViewModel.briefDate.observeAsState(
        initial = homeViewModel.today
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(brush = gradientBrush),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        //scroll
        var horizontalscrollState = rememberScrollState()

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
            },
            onSettingClick = onSettingClick
        )

        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .scrollable(horizontalscrollState, Orientation.Horizontal)
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Log.d(SERVER_TAG, briefDate.value.toString())
            items(homeViewModel.timeList) { time ->
                Column(
                    modifier = Modifier
                        .background(
                            color = if (briefDate.value == time) BriefingTheme.color.BackgroundWhite else Color.Transparent,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            homeViewModel.changeBriefDate(time)
                        }
                        .padding(vertical = 6.dp, horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //TODO: 요일 앞문자만 대문자로 수정하기
                    // TODO:
                    Text(
                        text = time.dayOfWeek.name.substring(0, 3),
                        style = BriefingTheme.typography.regular13.copy(color = if (briefDate.value == time) BriefingTheme.color.PrimaryBlue else BriefingTheme.color.BackgroundWhite)
                    )
                    Text(
                        text = time.dayOfMonth.toString(),
                        style = BriefingTheme.typography.bold30.copy(color = if (briefDate.value == time) BriefingTheme.color.PrimaryBlue else BriefingTheme.color.BackgroundWhite)
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
            style = BriefingTheme.typography.regular20.copy(
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
                    .clickable { onScrapClick() }
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
                BriefingTheme.color.BackgrundGray,
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
                style = BriefingTheme.typography.bold30.copy(color = BriefingTheme.color.PrimaryBlue)
            )
            Text(
                text = "Updated: ${briefingResponse.created_at}",
                style = BriefingTheme.typography.regular13
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
            .background(BriefingTheme.color.BackgroundWhite, shape = RoundedCornerShape(40.dp))
            .padding(vertical = 15.dp, horizontal = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            val backgroundColor = when (news.rank) {
                1 -> BriefingTheme.color.PrimaryBlue
                2 -> BriefingTheme.color.PrimaryBlue
                3 -> BriefingTheme.color.PrimaryBlue
                else -> BriefingTheme.color.BackgroundWhite
            }
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(color = backgroundColor, shape = RoundedCornerShape(50.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = news.rank.toString(), style = BriefingTheme.typography.bold20.copy(
                        color = if (backgroundColor == BriefingTheme.color.BackgroundWhite) BriefingTheme.color.PrimaryBlue else BriefingTheme.color.BackgroundWhite
                    ), overflow = TextOverflow.Ellipsis, maxLines = 1
                )

            }
            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .widthIn(max = 220.dp),
                verticalArrangement = Arrangement.Center,

                ) {
                Text(text = news.title, style = BriefingTheme.typography.bold20)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = news.subtitle,
                    style = BriefingTheme.typography.regular13,
                    maxLines = 1,
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


