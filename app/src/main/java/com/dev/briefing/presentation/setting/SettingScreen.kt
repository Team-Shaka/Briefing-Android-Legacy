package com.dev.briefing.presentation.setting

import android.app.*
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dev.briefing.BuildConfig
import com.dev.briefing.R
import com.dev.briefing.navigation.HomeScreen
import com.dev.briefing.presentation.login.SignInActivity
import com.dev.briefing.presentation.login.SignInViewModel
import com.dev.briefing.presentation.setting.component.SettingMenu
import com.dev.briefing.presentation.setting.component.SettingMenuItem
import com.dev.briefing.presentation.setting.component.SettingSection
import com.dev.briefing.presentation.theme.*
import com.dev.briefing.presentation.theme.utils.CommonDialog
import com.dev.briefing.util.ALARM_TAG
import com.dev.briefing.presentation.theme.component.CommonHeader
import com.dev.briefing.util.extension.convert.formatTime
import com.dev.briefing.util.preference.AuthPreferenceHelper
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onBackClick: () -> Unit,
    settingViewModel: SettingViewModel = koinViewModel()
) {
    val authPreferenceHelper: AuthPreferenceHelper = AuthPreferenceHelper(LocalContext.current)

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
                authViewModel.withdrawal(authPreferenceHelper.getMemberId())
                openExitDialog.value = false

                authPreferenceHelper.clearToken()
                authPreferenceHelper.clearMemberId()
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
                authPreferenceHelper.clearToken()
                authPreferenceHelper.clearMemberId()
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
            .background(color = BriefingTheme.color.BackgrundGray)
            .padding(bottom = 40.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        item {
            CommonHeader(
                onBackClick = onBackClick,
                header = "설정",
                color = BriefingTheme.color.BackgroundWhite
            )
            //알림
            SettingSection(title = R.string.setting_section_alarm)
            SettingMenuItem(
                type = SettingMenu(
                    isArrow = true,
                    text = formatTime(
                        dailyAlerTimeStateFlow.value.hour,
                        dailyAlerTimeStateFlow.value.minute
                    ),
                ),
                title = R.string.setting_alarm,
                onClick = {
                    timePickerDialog.show()
                },
            )
            //구독 정보
            SettingSection(R.string.setting_section_premium)
            SettingMenuItem(
                type = SettingMenu(
                    isArrow = true,
                ),
                title = R.string.setting_premium,
                onClick = {
                    navController.navigate(HomeScreen.Premium.route)
                },
            )
            //앱 정보
            SettingSection(R.string.setting_section_info)
            SettingMenuItem(
                type = SettingMenu(
                    isArrow = false,
                    text = BuildConfig.VERSION_NAME
                ),
                title = R.string.setting_version,
            )
            SettingMenuItem(
                type = SettingMenu(
                    isArrow = true,
                ),
                title = R.string.setting_feedback,
                onClick = {
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/HQXmEBkQ6wyW9jiw7"))
                    startActivity(context, intent, null)
                }
            )
            SettingMenuItem(
                type = SettingMenu(
                    isArrow = true,
                ),
                title = R.string.setting_version_note,
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://onve.notion.site/Briefing-8af692ff041c4fc6931b2fc897411e6d?pvs=4")
                    )
                    startActivity(context, intent, null)
                }
            )
            //개인 정보 보호
            SettingSection(R.string.setting_section_document)
            SettingMenuItem(
                type = SettingMenu(
                    isArrow = true,
                ),
                title = R.string.setting_policy,
                onClick = {
                    val intent =
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://sites.google.com/view/brieifinguse/%ED%99%88")
                        )
                    startActivity(context, intent, null)
                }
            )
            SettingMenuItem(
                type = SettingMenu(
                    isArrow = true,
                ),
                title = R.string.setting_policy_private,
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://sites.google.com/view/briefing-private/%ED%99%88")
                    )
                    startActivity(context, intent, null)
                }
            )
            SettingMenuItem(
                type = SettingMenu(
                    isArrow = true,
                ),
                title = R.string.setting_caution,
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://onve.notion.site/Briefing-e1cb17e2e7c54d3b9a7036b29ee9b11a?pvs=4")
                    )
                    startActivity(context, intent, null)
                }
            )
            //로그 아웃 및 회원 탈퇴
            SettingSection(R.string.setting_section_auth)
            SettingMenuItem(
                type = SettingMenu(
                    isArrow = true,
                ),
                title = R.string.setting_logout,
                onClick = {
                    Log.d(ALARM_TAG, openLogOutDialog.value.toString() + "최초 클릭")
                    openLogOutDialog.value = true
                }
            )
            SettingMenuItem(
                type = SettingMenu(
                    isArrow = true,
                ),
                title = R.string.setting_signout,
                titleColor = BriefingTheme.color.TextRed,
                onClick = {
                    Log.d(ALARM_TAG, openExitDialog.value.toString() + "최초 클릭")
                    openExitDialog.value = true
                }
            )
        }
    }
}
