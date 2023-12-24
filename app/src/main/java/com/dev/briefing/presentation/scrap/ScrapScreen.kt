package com.dev.briefing.presentation.scrap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dev.briefing.presentation.theme.component.CommonHeader
import com.dev.briefing.R
import com.dev.briefing.model.Scrap
import com.dev.briefing.model.tmpScrap
import com.dev.briefing.navigation.HomeScreen
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
    val scrap = viewModel.scrap.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = BriefingTheme.color.BackgroundWhite)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CommonHeader(
            header = "북마크한 브리핑",
            onBackClick = onBackClick,
            color = BriefingTheme.color.BackgroundWhite
        )
        if (scrap.value.isEmpty()) {
            ScrapDefaultScreen()
        } else {
            LazyColumn(
                modifier = Modifier.padding(vertical = 20.dp),
            ) {
                items(scrap.value.size){idx->
                    ScrapItem(scrap = scrap.value[idx], onItemClick = { id ->
                        navController.navigate("${HomeScreen.Detail.route}/$id")
                    })
                    HorizontalDivider(color = BriefingTheme.color.SeperatorGray)
                }

            }
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

@Preview
@Composable
fun PreviewScrapItem() {
    ScrapItem(
        scrap = tmpScrap,
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
    scrap: Scrap,
    onItemClick: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(scrap.briefingId)
            }
            .padding(vertical = 13.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "${scrap.date} ${scrap.timeOfDay}| 이슈 #${scrap.ranks} | ${scrap.gptModel}로 생성됨",
            style = BriefingTheme.typography.DetailStyleRegular.copy(
                color = BriefingTheme.color.TextGray
            )
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = scrap.title,
            style = BriefingTheme.typography.SubtitleStyleBold,
        )
        Text(
            text = scrap.subtitle,
            style = BriefingTheme.typography.ContextStyleRegular25.copy(
                color = BriefingTheme.color.TextGray,
            )
        )
    }


}