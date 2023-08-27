package com.dev.briefing.presentation.setting

import android.app.TimePickerDialog
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.briefing.R
import com.dev.briefing.data.Alarm
import com.dev.briefing.presentation.theme.*
import com.dev.briefing.util.SharedPreferenceHelper
import java.util.*

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var alarmTime:Alarm = SharedPreferenceHelper.getAlarm(context)
    var alarmHour = alarmTime.hour
    var alarmMinute = alarmTime.minute
    calendar.set(Calendar.HOUR_OF_DAY, alarmHour) // Set initial hour to 9 (9 AM)
    calendar.set(Calendar.MINUTE, alarmMinute)
    val timeState = remember { mutableStateOf(convertHour(alarmHour,alarmMinute)) }
    val timePickerDialog = TimePickerDialog(
        context,
        { view, hourOfDay, minute ->
            alarmHour = hourOfDay
            alarmMinute = minute
            if (hourOfDay >= 5 && hourOfDay <= 24) { // Check if selected hour is between 5 and 12
               timeState.value = convertHour(hourOfDay,minute)
                SharedPreferenceHelper.saveAlarm(context, Alarm(
                    hour = alarmHour,
                    minute = alarmMinute
                )
                )

            } else {
                Toast.makeText(context,"오전 5시부터 오후 12시까지만 설정가능합니다",Toast.LENGTH_LONG)
            }
        },
        calendar[Calendar.HOUR_OF_DAY],
        calendar[Calendar.MINUTE],
        false
    )
    LazyColumn(

        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = SubBackGround)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            CommonHeader(onBackClick = onBackClick, header = "설정")
            Spacer(modifier = Modifier.height(50.dp))
            menuWithArrow(
                isArrow = false,
                time =  timeState.value,
                icon = R.drawable.setting_clock,
                menu = R.string.setting_alarm,
                onClick = {
                    timePickerDialog.show()
                },
            )
            Spacer(modifier = Modifier.height(50.dp))
            menuWithArrow(
                icon = R.drawable.setting_version,
                menu = R.string.setting_version,
            )
            menuWithArrow(
                icon = R.drawable.setting_clock,
                menu = R.string.setting_feedback,
            )
            menuWithArrow(
                icon = R.drawable.setting_version_note,
                menu = R.string.setting_version_note,
            )
            Spacer(modifier = Modifier.height(50.dp))
            menuWithArrow(
                icon = R.drawable.setting_policy,
                menu = R.string.setting_policy,
            )
            Divider(
                color = BorderColor,
                thickness = 1.dp
            )
            menuWithArrow(
                icon = R.drawable.setting_policy,
                menu = R.string.setting_policy_private,
            )
            Divider(
                color = BorderColor,
                thickness = 1.dp
            )
            menuWithArrow(
                icon = R.drawable.setting_caution,
                menu = R.string.setting_caution,
            )
            Spacer(modifier = Modifier.height(50.dp))

            menuWithText(R.string.setting_logout)
            Divider(
                color = BorderColor,
                thickness = 1.dp
            )
            menuWithText(R.string.setting_signout)
            Spacer(modifier = Modifier.height(100.dp))

        }


    }
}

fun convertHour(
    hour:Int,
    minute:Int,
):String{
    var tmpString = ""
    if(hour<=12){
        tmpString = "오전 " + "${hour}시 ${minute}분"
    }else{
        tmpString = "오후 " + "${hour-12}시 ${minute}분"
    }
    return tmpString
}
@Composable
fun menuWithText(
    @StringRes menu: Int = R.string.navigation_chat,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = menu),
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight(400),
                color = SubText2
            )
        )
    }
}

@Composable
fun menuWithArrow(
    time: String ="",
    isArrow:Boolean=true,
    @DrawableRes icon: Int = R.drawable.setting_caution,
    @StringRes menu: Int = R.string.navigation_chat,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .clickable(onClick=onClick ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "더보기"
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(id = menu),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight(400)
                )
            )
        }
        if(isArrow){
            Image(
                painter = painterResource(id = R.drawable.left_arrow),
                contentDescription = "더보기"
            )
        }else{
            Text(
                text = time,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight(400),
                    color = MainPrimary3
                )
            )
        }


    }
}

@Composable
fun CommonHeader(
    onBackClick: () -> Unit,
    header: String = ""
) {
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
                id = R.drawable.vector
            ),
            contentDescription = "뒤로가기", modifier = Modifier
                .clickable(onClick = onBackClick)
        )
        Text(
            text = header,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MainPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight(400)
            )
        )
        Text(
            text = "",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MainPrimary
            )
        )

    }
}