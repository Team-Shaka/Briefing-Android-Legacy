package com.dev.briefing.presentation.setting

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.startActivity
import com.dev.briefing.BuildConfig.NOTIFICATION_CHANNEL_ID
import com.dev.briefing.R
import com.dev.briefing.data.Alarm
import com.dev.briefing.presentation.setting.alarm.AlarmReceiver
import com.dev.briefing.presentation.theme.*
import com.dev.briefing.util.ALARM_TAG
import com.dev.briefing.util.SharedPreferenceHelper
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var alarmTime: Alarm = SharedPreferenceHelper.getAlarm(context)
    var alarmHour = alarmTime.hour
    var alarmMinute = alarmTime.minute
    calendar.set(Calendar.HOUR_OF_DAY, alarmHour) // Set initial hour to 9 (9 AM)
    calendar.set(Calendar.MINUTE, alarmMinute)
    val timeState = remember { mutableStateOf(convertHour(alarmHour, alarmMinute)) }
    val timePickerDialog = TimePickerDialog(
        context,
        { view, hourOfDay, minute ->
            alarmHour = hourOfDay
            alarmMinute = minute
            if (hourOfDay >= 5 && hourOfDay <= 24) { // Check if selected hour is between 5 and 12
                timeState.value = convertHour(hourOfDay, minute)
                SharedPreferenceHelper.savePreference(
                    context, Alarm(
                        hour = alarmHour,
                        minute = alarmMinute
                    )
                )
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay) // Set initial hour to 9 (9 AM)
                calendar.set(Calendar.MINUTE, minute)

            } else {
                Toast.makeText(context, "오전 5시부터 오후 12시까지만 설정가능합니다", Toast.LENGTH_LONG)
            }

        },
        calendar[Calendar.HOUR_OF_DAY],
        calendar[Calendar.MINUTE],
        false
    )
    Log.d(ALARM_TAG,calendar.get(Calendar.HOUR_OF_DAY).toString()+"시" +calendar.get(Calendar.MINUTE).toString())
    val alarmTimeInMillis = calendar.timeInMillis

    LaunchedEffect(key1 = alarmTimeInMillis) {
        //get Notificaton Manager
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // AlarmReceiver를 수신할 Intent를 생성하고, 이를 PendingIntent로 래핑합니다.
        // PendingIntent는 앞으로 발생할 이벤트를 대리자
        val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        //get Alarm Manager
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //RTC_WAKEUP은 실제 시간으로 알람을 설정하되, 장치가 슬립 상태일 때도 깨우도록 합니다.
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, alarmIntent)

        val notificationId = System.currentTimeMillis().toInt()
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Alarm")
            .setContentText("Alarm is set for ${SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(alarmTimeInMillis))}")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        Log.d(ALARM_TAG,"알람 채널 id${notification.channelId}이고 알람${notification.visibility}이다")
        notificationManager.notify(notificationId, notification)

        val activeNotifications = notificationManager.activeNotifications

        for (notification in activeNotifications) {
            val notificationId = notification.id
            val notificationTag = notification.tag
            val notificationKey = notification.key
            Log.d(ALARM_TAG,"알람 채널 id${notificationId}이고 알람${notificationTag}이다 ${notificationKey}")
        }
    }
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
                time = timeState.value,
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
                time = "1.0.0",
                isArrow = false
            )
            menuWithArrow(
                icon = R.drawable.setting_clock,
                menu = R.string.setting_feedback,
                onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/HQXmEBkQ6wyW9jiw7"))
                startActivity(context, intent, null)
            }
            )
            menuWithArrow(
                icon = R.drawable.setting_version_note,
                menu = R.string.setting_version_note,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://onve.notion.site/Briefing-8af692ff041c4fc6931b2fc897411e6d?pvs=4"))
                    startActivity(context, intent, null)
                }
            )
            Spacer(modifier = Modifier.height(50.dp))
            menuWithArrow(
                icon = R.drawable.setting_policy,
                menu = R.string.setting_policy,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/brieifinguse/%ED%99%88"))
                    startActivity(context, intent, null)
                }
            )
            Divider(
                color = BorderColor,
                thickness = 1.dp
            )
            menuWithArrow(
                icon = R.drawable.setting_policy,
                menu = R.string.setting_policy_private,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/briefing-private/%ED%99%88"))
                    startActivity(context, intent, null)
                }
            )
            Divider(
                color = BorderColor,
                thickness = 1.dp
            )
            menuWithArrow(
                icon = R.drawable.setting_caution,
                menu = R.string.setting_caution,
                onClick = {
                    //TODO: URL 변경
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/briefing-private/%ED%99%88"))
                    startActivity(context, intent, null)
                }
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
@Composable
fun setAlarmTime(alarmTime: Long,context: Context){
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, alarmIntent)

    val notificationId = 0
    val notification = NotificationCompat.Builder(context, "channelId")
        .setContentTitle("Alarm")
        .setContentText("Alarm is set for ${SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(alarmTime))}")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    notificationManager.notify(notificationId, notification)
}
fun convertHour(
    hour: Int,
    minute: Int,
): String {
    var tmpString = ""
    if (hour <= 12) {
        tmpString = "오전 " + "${hour}시 ${minute}분"
    } else {
        tmpString = "오후 " + "${hour - 12}시 ${minute}분"
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
    time: String = "",
    isArrow: Boolean = true,
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
            .clickable(onClick = onClick),
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
        if (isArrow) {
            Image(
                painter = painterResource(id = R.drawable.left_arrow),
                contentDescription = "더보기"
            )
        } else {
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