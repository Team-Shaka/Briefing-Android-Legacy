package com.dev.briefing.presentation.detail

import android.content.Context
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dev.briefing.R
import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.tmpBriefingResponse
import com.dev.briefing.presentation.theme.BriefingTheme
import com.dev.briefing.presentation.theme.GradientEnd
import com.dev.briefing.presentation.theme.GradientStart
import com.dev.briefing.presentation.theme.SubText2
import com.dev.briefing.presentation.theme.Typography
import com.dev.briefing.presentation.theme.White
import com.dev.briefing.util.SCRAP_TAG
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ArticleDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    id: Int
) {
    val context = LocalContext.current
    //viewmodel
    val articleDetailViewModel: ArticleDetailViewModel = getViewModel {
        parametersOf(id)
    }
    //aritcleResponse
    val articleResponse = articleDetailViewModel.detailPage.observeAsState(
        initial = tmpBriefingResponse
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd),
                    startY = 0.0f,
                    endY = LocalConfiguration.current.screenHeightDp.toFloat()
                )
            )
            .padding(horizontal = 30.dp)

    ) {
        //backKey, ranking, scrap 기능이 포함된 header
        Log.d(SCRAP_TAG, "0. 파라미터로 전달하기 전 isScrapStatus 값 : ${articleResponse.value.isScrap}")
        DetailHeader(
            onBackClick = onBackClick,
            scrap = articleDetailViewModel.setScrap(),
            unScrap = articleDetailViewModel.unScrap(),
            rank = articleResponse.value.rank,
            isScrap = articleResponse.value.isScrap,
            context = context,
        )
        Spacer(modifier = Modifier.height(34.dp))
        //article List
        LazyColumn {
            item {
                ArticleDetail(
                    article = articleResponse.value,
                    context = context
                )
                Spacer(modifier = Modifier.height(25.dp))
            }
        }
    }
}

@Composable
fun ArticleDetail(
    modifier: Modifier = Modifier,
    article: BriefingDetailResponse,
    context: Context
) {
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
                text = "${article.date} Breifing #${article.rank}",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = SubText2,
                    fontWeight = FontWeight(400)

                ),
                textAlign = TextAlign.End
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = article.title,
                style = Typography.titleLarge
            )
            if (article.isWarning) {
                Image(
                    painter = painterResource(id = R.drawable.home_alert),
                    colorFilter = ColorFilter.tint(
                        BriefingTheme.color.PrimaryBlue
                    ),
                    contentDescription = "fdfd"
                )
            }

        }
        Text(
            text = article.subtitle,
            style = Typography.headlineLarge
        )
        Text(
            text = article.content,
            style = Typography.bodyMedium.copy(
                fontWeight = FontWeight(400),
                color = BriefingTheme.color.PrimaryBlue
            )
        )
        Text(
            text = stringResource(R.string.detail_article_header),
            style = Typography.headlineLarge
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            article.articles.forEach { it ->
                ArticleLink(
                    newsLink = it,
                    context = context
                )
            }
        }
        Spacer(modifier = Modifier.height(25.dp))

    }
}