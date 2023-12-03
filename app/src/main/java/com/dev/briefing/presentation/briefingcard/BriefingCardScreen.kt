package com.dev.briefing.presentation.briefingcard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dev.briefing.R
import com.dev.briefing.presentation.theme.BriefingTheme

@Composable
@Preview
fun BriefingCardScreenPreview() {
    BriefingTheme {
        BriefingCardScreen(rememberNavController()) {

        }
    }
}

@Composable
fun BriefingCardScreen(navController: NavController, onBackClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = BriefingTheme.color.BackgroundWhite)) {
        TopBar(onBackPressed = onBackClick, onMenuButtonPressed = {})

        BriefingCardHeader(
            modifier = Modifier.padding(30.dp, 18.dp),
            title = LoremIpsum(3).values.joinToString(),
            date = "1970.01.01 아침",
            section = "사회 #1",
            generatedEngine = "GPT-3로 생성됨",
            bookmarkCount = 149
        )

        Spacer(
            modifier = Modifier
                .padding(20.dp, 0.dp)
                .fillMaxWidth()
                .height(0.5.dp)
                .background(color = Color(0xFFDADADA))
        )

        BriefingCardSummary(
            modifier = Modifier.padding(30.dp, 20.dp),
            summaryTitle = LoremIpsum(3).values.joinToString(),
            summaryContent = LoremIpsum(30).values.joinToString()
        )

        Spacer(
            modifier = Modifier
                .padding(20.dp, 0.dp)
                .fillMaxWidth()
                .height(0.5.dp)
                .background(color = Color(0xFFDADADA))
        )

        RelatedArticles(Modifier.padding(32.dp, 16.dp))
    }
}

@Composable
fun RelatedArticles(modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = "관련 기사",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF000000)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(2) {
                RelatedArticle(
                    LoremIpsum(3).values.joinToString(),
                    LoremIpsum(4).values.joinToString()
                )
            }
        }
    }
}

@Preview
@Composable
fun RelatedArticlePreview() {
    BriefingTheme {
        RelatedArticle(LoremIpsum(3).values.joinToString(), LoremIpsum(4).values.joinToString())
    }
}

@Composable
fun RelatedArticle(title: String, description: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black)
            .padding(16.dp, 10.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000)
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                )
            )
        }

        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.ic_left_arrow_slim),
                contentDescription = null
            )
        }

    }
}

@Preview
@Composable
fun BriefingCardSummaryPreview() {
    BriefingTheme {
        BriefingCardSummary(
            Modifier.fillMaxWidth(),
            LoremIpsum(3).values.joinToString(),
            LoremIpsum(20).values.joinToString()
        )
    }
}

@Composable
fun BriefingCardSummary(
    modifier: Modifier = Modifier,
    summaryTitle: String,
    summaryContent: String
) {
    Column(modifier) {
        Text(
            text = summaryTitle,
            style = BriefingTheme.typography.TitleStyleBold.merge(
                fontSize = 20.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = summaryContent,
            style = BriefingTheme.typography.DetailStyleRegular.merge(
                fontSize = 17.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
            )
        )
    }
}

@Preview
@Composable
fun BriefingCardHeaderPreview() {
    BriefingTheme {
        BriefingCardHeader(
            title = LoremIpsum(3).values.joinToString(),
            date = "1970.01.01 아침",
            section = "사회 #1",
            generatedEngine = "GPT-3로 생성됨",
            bookmarkCount = 763
        )
    }
}

@Composable
fun BriefingCardHeader(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    section: String,
    generatedEngine: String,
    bookmarkCount: Int
) {
    Row(modifier) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Black
                ), text = title
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF7C7C7C),
                ), text = "$date | $section | $generatedEngine"
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = bookmarkCount.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF7C7C7C)
                )
            )

            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.bookmark_breifingcard),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun TopBar(onBackPressed: () -> Unit, onMenuButtonPressed: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(12.dp, 4.dp)
    ) {

        IconButton(onClick = onBackPressed, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(
                modifier = Modifier.size(16.dp, 16.dp),
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = null
            )
        }

        IconButton(onClick = onBackPressed, modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                contentDescription = null
            )
        }
    }
}