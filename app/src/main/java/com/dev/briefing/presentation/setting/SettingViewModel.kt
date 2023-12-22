package com.dev.briefing.presentation.setting

import androidx.lifecycle.ViewModel
import com.dev.briefing.data.AlarmTime
import com.dev.briefing.util.dailyalert.DailyAlertManager
import com.dev.briefing.util.preference.DailyAlertTimePreferenceHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingViewModel(
    private val dailyAlertTimePreferenceHelper: DailyAlertTimePreferenceHelper,
    private val dailyAlertManager: DailyAlertManager
) : ViewModel() {
    private val _notifyTimeStateFlow =
        MutableStateFlow(dailyAlertTimePreferenceHelper.getAlarmTime())
    val notifyTimeStateFlow = _notifyTimeStateFlow.asStateFlow()

    fun changeDailyAlarmTime(hourOfDay: Int, minute: Int) {
        dailyAlertTimePreferenceHelper.saveAlarmTime(AlarmTime(hourOfDay, minute))

        _notifyTimeStateFlow.update {
            dailyAlertTimePreferenceHelper.getAlarmTime()
        }

        dailyAlertManager.setDailyAlarm(hourOfDay, minute)
    }

    fun withdrawal() {

    }
}