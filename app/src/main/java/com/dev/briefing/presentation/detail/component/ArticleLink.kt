package com.dev.briefing.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.dev.briefing.R
import com.dev.briefing.data.model.Article
import com.dev.briefing.presentation.theme.BriefingTheme

@Composable
fun ArticleLink(
    newsLink: Article,
    modifier: Modifier = Modifier,
    context: Context
) {
    Row(
        modifier
            .fillMaxWidth()
            .background(BriefingTheme.color.BackgroundWhite, shape = RoundedCornerShape(40.dp))
            .border(1.dp, BriefingTheme.color.PrimaryBlue, shape = RoundedCornerShape(10.dp))
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
                text = newsLink.press, style = BriefingTheme.typography.DetailStyleRegular.copy(
                    fontWeight = FontWeight(700),
                    color = BriefingTheme.color.PrimaryBlue
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = newsLink.title,
                style = BriefingTheme.typography.DetailStyleRegular,
                overflow = TextOverflow.Ellipsis
            )
        }
        Image(painter = painterResource(id = R.drawable.left_arrow), contentDescription = "fdfd")
    }
}