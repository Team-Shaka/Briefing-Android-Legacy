package com.dev.briefing.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.dev.briefing.presentation.theme.BriefingTheme

@Preview
@Composable
fun BriefingTabPreview() {
    BriefingTheme {
        BriefingTabRow(
            tabs = listOf("사회", "과학", "글로벌", "경제"),
            selectedTabIndex = 2,
            onTabSelected = {

            })
    }
}

@Composable
fun BriefingTabRow(tabs: List<String>, selectedTabIndex: Int, onTabSelected: (index: Int) -> Unit) {
    val positionMap = remember {
        mutableStateMapOf<Int, Pair<Float, Float>>()
    }

    Column(Modifier.fillMaxWidth()) {
        // 탭
        Row(Modifier.fillMaxWidth()) {
            tabs.forEachIndexed { index, tab ->
                Box(Modifier.onGloballyPositioned {
                    // Composable 요소의 x 위치
                    val startX = it.positionInParent().x
                    // Composable 요소의 너비
                    val width = it.size.width
                    // 오른쪽 끝 좌표 계산
                    val endX = startX + width
                    // Map에 시작 좌표와 끝 좌표 저장
                    positionMap[index] = Pair(startX, endX)
                }) {
                    Text(
                        modifier = Modifier.padding(24.dp, 16.dp),
                        text = tab, style = BriefingTheme.typography.ContextStyleRegular25
                    )
                }
            }
        }


        val indiciatorWidth = 42.dp
        // 인디케이터
        Row(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color(0xffe7e7e7))
        ) {
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .width(indiciatorWidth)
                    .offset(x = with(LocalDensity.current) {
                        ((positionMap[selectedTabIndex]?.first)?.toDp() ?: 0.dp) +
                                ((((positionMap[selectedTabIndex]?.second)?.toDp() ?: 0.dp) -
                                        ((positionMap[selectedTabIndex]?.first)?.toDp() ?: 0.dp) -
                                        indiciatorWidth) / 2)
                    })
                    .background(BriefingTheme.color.PrimaryBlue)
            )
        }
    }
}