package com.dev.briefing.presentation.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.dev.briefing.R
import com.dev.briefing.data.model.Article
import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.tmpBriefingResponse
import com.dev.briefing.presentation.theme.GradientEnd
import com.dev.briefing.presentation.theme.GradientStart
import com.dev.briefing.presentation.theme.MainPrimary
import com.dev.briefing.presentation.theme.MainPrimary4
import com.dev.briefing.presentation.theme.SubText2
import com.dev.briefing.presentation.theme.Typography
import com.dev.briefing.presentation.theme.White
import com.dev.briefing.presentation.theme.utils.CommonDialog
import com.dev.briefing.util.MEMBER_ID
import com.dev.briefing.util.MainApplication
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
fun DetailHeader(
    onBackClick: () -> Unit,
    scrap: () -> Boolean,
    unScrap: () -> Boolean,
    rank: Int,
    context: Context,
    isScrap: Boolean
) {
    // 로그인이 안된 상태에서 스크랩 시도시 로그인 다이얼로그
    val openLogInDialog = remember { mutableStateOf(false) }
    if (openLogInDialog.value) {
        CommonDialog(
            onDismissRequest = { openLogInDialog.value = false },
            onConfirmation = {
                openLogInDialog.value = false
                (context as Activity).finish()
            },
            dialogTitle = R.string.dialog_login_title,
            dialogText = R.string.dialog_login_text,
            dialogId = R.string.dialog_login_confirm,
            confirmColor = MainPrimary4
        )
    }
    /**  스크랩 관련 변수
     * 1. scrap img
     * 2.
     */

    //1. scrapImg resource 정의
    val unScrapImg = painterResource(id = R.drawable.scrap_normal)
    val scrapImg = painterResource(id = R.drawable.scrap_selected)

    //2. scrapStatus 정의
    val isScrapStatus = remember {
        //파라미터로 받은 isScrap값으로 초기화 해준다
        mutableStateOf(false)
    }
    LaunchedEffect(isScrap) {
        isScrapStatus.value = isScrap
    }
    Log.d(SCRAP_TAG, "1. 파라미터로 전달받은 isScrap 값: ${isScrap}, isScrapStatus 값: ${isScrapStatus}")



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
            contentDescription = "back Key", modifier = Modifier
                .clickable(onClick = onBackClick)
        )
        Text(
            text = "Briefing #${rank}",
            style = Typography.titleMedium.copy(
                color = White,
                fontWeight = FontWeight(400)
            )
        )
        Image(
            painter = if (isScrapStatus.value) scrapImg else unScrapImg,
            contentDescription = if (isScrapStatus.value) "Unliked" else "Liked",
            modifier = Modifier.clickable(
                onClick = {
                    Log.d(SCRAP_TAG, "2. 클릭이벤트 발생! 변경전 scrap 값 : ${isScrapStatus.value}")
                    val memberId: Int = MainApplication.prefs.getSharedPreference(MEMBER_ID, 0)
                    if (memberId != 0) {
                        if (isScrapStatus.value) {
                            Log.d(SCRAP_TAG, "3. 스크랩 취소 : ${isScrapStatus.value}")
                            if (unScrap()) {
                                isScrapStatus.value = false
                            }else{
                                Toast.makeText(context, "스크랩 해제에 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Log.d(SCRAP_TAG, "3. 스크랩 등록")
                            if (scrap()) {
                                isScrapStatus.value = true
                            }else{
                                Toast.makeText(context, "스크랩에 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                            }
                        }
                        Log.d(SCRAP_TAG, "4. 클릭이벤트 발생! 변경전 scrap 값 : ${isScrapStatus.value}")

                    } else {
                        openLogInDialog.value = true
                    }
                }
            )
        )

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
                        MainPrimary
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
                color = MainPrimary
            )
        )
        Text(
            text = stringResource(R.string.detail_article_header),
            style = Typography.headlineLarge
        )
        if (article.isBriefOpen) {
            BriefChatLink()
        }
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

@Composable
fun ArticleLink(
    newsLink: Article,
    modifier: Modifier = Modifier,
    context: Context
) {
    Row(
        modifier
            .fillMaxWidth()
            .background(White, shape = RoundedCornerShape(40.dp))
            .border(1.dp, MainPrimary, shape = RoundedCornerShape(10.dp))
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsLink.url))
                ContextCompat.startActivity(context, intent, null)
            }
            .padding(vertical = 9.dp, horizontal = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.widthIn(max = 193.dp)
        ) {
            Text(
                text = newsLink.press, style = Typography.bodyMedium.copy(
                    fontWeight = FontWeight(700),
                    color = MainPrimary
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = newsLink.title,
                style = Typography.labelSmall,
                overflow = TextOverflow.Ellipsis
            )
        }
        Image(painter = painterResource(id = R.drawable.left_arrow), contentDescription = "fdfd")
    }
}

/**
 * [ChatScreen]으로 이동하는 버튼
 */
@Preview
@Composable
fun BriefChatLink(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .fillMaxWidth()
            .background(White, shape = RoundedCornerShape(40.dp))
            .border(1.dp, MainPrimary, shape = RoundedCornerShape(10.dp))
            .clickable {
                //TODO: add ChatScreen navigate
            }
            .padding(vertical = 9.dp, horizontal = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.chat_selelcted),
            contentDescription = "fdfd"
        )
        Spacer(modifier = Modifier.width(9.dp))
        Text(
            text = stringResource(R.string.detail_brief_text), style = Typography.bodyMedium.copy(
                fontWeight = FontWeight(700),
                color = MainPrimary
            )
        )
        Text(
            text = stringResource(R.string.detail_brief_label),
            style = Typography.labelSmall.copy(
                color = MainPrimary,
                fontSize = 7.sp,
                lineHeight = 8.sp
            ),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )

        Image(painter = painterResource(id = R.drawable.left_arrow), contentDescription = "fdfd")
    }
}