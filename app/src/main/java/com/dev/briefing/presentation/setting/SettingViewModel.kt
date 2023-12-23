package com.dev.briefing.presentation.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.AlarmTime
import com.dev.briefing.data.respository.AuthRepository
import com.dev.briefing.util.SERVER_TAG
import com.dev.briefing.util.dailyalert.DailyAlertManager
import com.dev.briefing.util.preference.AuthPreferenceHelper
import com.dev.briefing.util.preference.DailyAlertTimePreferenceHelper
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingViewModel(
    private val dailyAlertTimePreferenceHelper: DailyAlertTimePreferenceHelper,
    private val dailyAlertManager: DailyAlertManager,
    private val authRepository: AuthRepository,
    private val authPreferenceHelper: AuthPreferenceHelper
) : ViewModel() {
    private val _notifyTimeStateFlow =
        MutableStateFlow(dailyAlertTimePreferenceHelper.getAlarmTime())
    val notifyTimeStateFlow = _notifyTimeStateFlow.asStateFlow()

    private val _settingUiState =
        MutableStateFlow<SettingUiState>(SettingUiState.Default)

    val settingUiState = _settingUiState.asStateFlow()

    fun changeDailyAlarmTime(hourOfDay: Int, minute: Int) {
        dailyAlertTimePreferenceHelper.saveAlarmTime(AlarmTime(hourOfDay, minute))

        _notifyTimeStateFlow.update {
            dailyAlertTimePreferenceHelper.getAlarmTime()
        }

        dailyAlertManager.setDailyAlarm(hourOfDay, minute)
    }

    fun deleteUser() {
        val memberId = authPreferenceHelper.getMemberId()
        if (memberId == -1) {
            return
        }

        _settingUiState.update {
            SettingUiState.AccountDeletionCompleted
        }

        viewModelScope.launch {
            runCatching {
                authRepository.deleteMember(memberId)
                authPreferenceHelper.clearToken()
                authPreferenceHelper.clearMemberId()
            }.onSuccess {
                _settingUiState.update {
                    SettingUiState.AccountDeletionCompleted
                }
            }.onFailure {
                Logger.e(it.message ?: "error in deleteUser")
            }
        }
    }
}