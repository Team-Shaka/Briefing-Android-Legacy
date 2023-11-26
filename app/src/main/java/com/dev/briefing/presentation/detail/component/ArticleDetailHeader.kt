package com.dev.briefing.presentation.detail

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dev.briefing.R
import com.dev.briefing.presentation.theme.BriefingTheme
import com.dev.briefing.presentation.theme.utils.CommonDialog
import com.dev.briefing.util.MEMBER_ID
import com.dev.briefing.util.MainApplication
import com.dev.briefing.util.SCRAP_TAG

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
            confirmColor = BriefingTheme.color.PrimaryBlue
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
            style = BriefingTheme.typography.SmallcontextStyleRegular.copy(
                color = BriefingTheme.color.BackgroundWhite,
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
                            } else {
                                Toast.makeText(
                                    context,
                                    "스크랩 해제에 실패하였습니다. 다시 시도해주세요",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Log.d(SCRAP_TAG, "3. 스크랩 등록")
                            if (scrap()) {
                                isScrapStatus.value = true
                            } else {
                                Toast.makeText(
                                    context,
                                    "스크랩에 실패하였습니다. 다시 시도해주세요",
                                    Toast.LENGTH_SHORT
                                ).show()
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