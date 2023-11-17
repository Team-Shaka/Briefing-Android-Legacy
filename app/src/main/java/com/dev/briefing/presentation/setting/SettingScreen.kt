package com.dev.briefing.presentation.setting

import android.app.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ComponentActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dev.briefing.BuildConfig.NOTIFICATION_CHANNEL_ID
import com.dev.briefing.R
import com.dev.briefing.presentation.login.SignInActivity
import com.dev.briefing.presentation.login.SignInViewModel
import com.dev.briefing.presentation.theme.*
import com.dev.briefing.presentation.theme.utils.CommonDialog
import com.dev.briefing.util.ALARM_CODE
import com.dev.briefing.util.ALARM_TAG
import com.dev.briefing.util.JWT_TOKEN
import com.dev.briefing.util.MEMBER_ID
import com.dev.briefing.util.MainApplication.Companion.prefs
import com.dev.briefing.util.REFRESH_TOKEN
import com.dev.briefing.util.SharedPreferenceHelper
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    settingViewModel : SettingViewModel = koinViewModel()
) {
    val authViewModel: SignInViewModel = getViewModel()
    val context = LocalContext.current
    val openLogOutDialog = remember { mutableStateOf(false) }
    val openExitDialog = remember { mutableStateOf(false) }

    val dailyAlerTimeStateFlow = settingViewModel.notifyTimeStateFlow.collectAsStateWithLifecycle()

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            //알람 가능 시간 분기처리
            if (hourOfDay in 5..24) {
                settingViewModel.changeDailyAlarmTime(hourOfDay, minute)
            } else {
                Toast.makeText(context, "오전 5시부터 오후 12시까지만 설정가능합니다", Toast.LENGTH_LONG).show()
            }

        },
        dailyAlerTimeStateFlow.value.hour,
        dailyAlerTimeStateFlow.value.minute,
        false
    )
    if (openExitDialog.value) {
        Log.d(ALARM_TAG, openExitDialog.value.toString())
        CommonDialog(
            onDismissRequest = { openExitDialog.value = false },
            onConfirmation = {
                authViewModel.signout(prefs.getSharedPreference(MEMBER_ID, -1))
                openExitDialog.value = false

                prefs.removeSharedPreference(MEMBER_ID)
                prefs.removeSharedPreference(JWT_TOKEN)
                prefs.removeSharedPreference(REFRESH_TOKEN)
                openLogOutDialog.value = false

                val intent = Intent(context, SignInActivity::class.java)
                startActivity(context, intent, null)
                val activity = context as? ComponentActivity
                activity?.finish()

            },
            dialogTitle = R.string.dialog_exit_title,
            dialogText = R.string.dialog_exit_text,
            dialogId = R.string.dialog_exit_confirm
        )

    }
    if (openLogOutDialog.value) {
        CommonDialog(
            onDismissRequest = { openLogOutDialog.value = false },
            onConfirmation = {
                prefs.removeSharedPreference(MEMBER_ID)
                prefs.removeSharedPreference(JWT_TOKEN)
                prefs.removeSharedPreference(REFRESH_TOKEN)
                openLogOutDialog.value = false

                val intent = Intent(context, SignInActivity::class.java)
                startActivity(context, intent, null)
                val activity = context as? ComponentActivity
                activity?.finish()
            },
            dialogTitle = R.string.dialog_logout_title,
            dialogText = R.string.dialog_logout_text,
            dialogId = R.string.dialog_logout_confirm
        )


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
                time = formatTime(
                    dailyAlerTimeStateFlow.value.hour,
                    dailyAlerTimeStateFlow.value.minute
                ),
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
                time = "1.1.1",
                isArrow = false
            )
            menuWithArrow(
                icon = R.drawable.setting_clock,
                menu = R.string.setting_feedback,
                onClick = {
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/HQXmEBkQ6wyW9jiw7"))
                    startActivity(context, intent, null)
                }
            )
            menuWithArrow(
                icon = R.drawable.setting_version_note,
                menu = R.string.setting_version_note,
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://onve.notion.site/Briefing-8af692ff041c4fc6931b2fc897411e6d?pvs=4")
                    )
                    startActivity(context, intent, null)
                }
            )
            Spacer(modifier = Modifier.height(50.dp))
            menuWithArrow(
                icon = R.drawable.setting_policy,
                menu = R.string.setting_policy,
                onClick = {
                    val intent =
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://sites.google.com/view/brieifinguse/%ED%99%88")
                        )
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
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://sites.google.com/view/briefing-private/%ED%99%88")
                    )
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
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://onve.notion.site/Briefing-e1cb17e2e7c54d3b9a7036b29ee9b11a?pvs=4")
                    )
                    startActivity(context, intent, null)
                }
            )
            Spacer(modifier = Modifier.height(50.dp))

            menuWithText(R.string.setting_logout, onClick = {
                Log.d(ALARM_TAG, openLogOutDialog.value.toString() + "최초 클릭")
                openLogOutDialog.value = true
            })
            Divider(
                color = BorderColor,
                thickness = 1.dp
            )
            menuWithText(R.string.setting_signout, onClick = {
                Log.d(ALARM_TAG, openExitDialog.value.toString() + "최초 클릭")
                openExitDialog.value = true
            }, color = DialogExit)
            Spacer(modifier = Modifier.height(100.dp))

        }


    }
}

fun formatTime(
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
    onClick: () -> Unit = {},
    color: Color = MainPrimary2,
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
        Text(
            text = stringResource(id = menu),
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight(400),
                color = color
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
    header: String = "",
    color: Color = SubBackGround
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = color)
            .padding(top = 60.dp, bottom = 20.dp),
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