package com.dev.briefing.presentation.theme.utils

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dev.briefing.R
import com.dev.briefing.presentation.theme.DialogButtonExit
import com.dev.briefing.presentation.theme.DisableButton
import com.dev.briefing.presentation.theme.ErrorColor
import com.dev.briefing.presentation.theme.Typography
import com.dev.briefing.presentation.theme.White

fun Modifier.drawColoredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = this.drawBehind {
    val transparentColor = android.graphics.Color.toArgb(color.copy(alpha = 0.0f).value.toLong())
    val shadowColor = android.graphics.Color.toArgb(color.copy(alpha = alpha).value.toLong())
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            borderRadius.toPx(),
            borderRadius.toPx(),
            paint
        )
    }
}

@Composable
fun alertWidget() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.home_alert),
            contentDescription = "alert"
        )
        Spacer(modifier = Modifier.height(39.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "인터넷이 연결되지 않았습니다.\n" +
                    "연결 상태를 확인해 주세요.",
            style = Typography.titleSmall.copy(
                color = ErrorColor
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CommonDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    dialogId: Int,
    confirmColor: Color = DialogButtonExit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .background(color = White, shape = RoundedCornerShape(10.dp))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = dialogTitle,
                style = Typography.titleMedium.copy(fontSize = 20.sp, color = Black)
            )
            Text(
                text = dialogText,
                style = Typography.headlineLarge.copy(
                    color = Black,
                    fontWeight = FontWeight.Normal
                ), textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .background(color = DisableButton, shape = RoundedCornerShape(size = 10.dp))
                        .weight(1f)
                        .align(alignment = Alignment.CenterVertically)
                        .padding(vertical = 15.dp)
                        .clickable(onClick = onDismissRequest),
                    text = stringResource(
                        R.string.dialog_basic_dismiss,
                    ),
                    style = Typography.headlineLarge.copy(color = Black),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    modifier = Modifier
                        .background(
                            color = confirmColor,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .weight(1f)
                        .padding(vertical = 15.dp)
                        .clickable(onClick = onConfirmation),
                    text = stringResource(
                        dialogId
                    ),
                    style = Typography.headlineLarge.copy(color = White),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}