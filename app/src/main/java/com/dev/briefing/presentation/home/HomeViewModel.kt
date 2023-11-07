package com.dev.briefing.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.model.BriefingPreview
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.respository.BriefingRepository
import com.dev.briefing.util.SERVER_TAG
import com.dev.briefing.util.UPDATE_DATE
import com.dev.briefing.util.dailyalert.DailyAlertManager
import com.dev.briefing.util.preference.DailyAlertTimePreferenceHelper
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel(
    private val repository: BriefingRepository,
    private val dailyAlertManager: DailyAlertManager,
    private val dailyAlertTimePreferenceHelper: DailyAlertTimePreferenceHelper
) : ViewModel() {

    private val _serverTestResponse: MutableLiveData<BriefingResponse> =
        MutableLiveData<BriefingResponse>()
    val serverTestResponse: LiveData<BriefingResponse>
        get() = _serverTestResponse

    //time관련 변수
    var timeList: MutableList<LocalDate> = mutableListOf()
    val today: LocalDate = LocalDate.now()
    //TODO: time add start 출시일 ~ end 오늘날

    // 변경 가능한 LiveData를 선언
    private var _briefDate = MutableLiveData<LocalDate>()

    // 외부로 불변성을 유지하기 위해 공개하는 LiveData
    val briefDate: LiveData<LocalDate> = _briefDate

    init {
        _briefDate.value = today
        getBriefingDataApi(today)
        setDateList()
    }

    fun getBriefingDataApi(date: LocalDate) {
        viewModelScope.launch {
            try {
                val response = repository.getBriefingKeyword(
                    briefingDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    type = "Korea"
                )
                if (response.result != null) {
                    _serverTestResponse.value = response.result!!
                }
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
            }
            Log.d(SERVER_TAG, "끝!")
        }

    }

    //click 이벤트가 발생하면 호출되는 함수
    fun changeBriefDate(date: LocalDate) {
        _briefDate.value = date
        Log.d("날짜", _briefDate.value.toString())
        getBriefingDataApi(date)
    }

    fun setDateList() {
        var updateDate: LocalDate = today.minusDays(1)
        timeList.add(today)
        while (timeList.size < 7 && updateDate.isAfter(UPDATE_DATE)) {
            timeList.add(updateDate)
            Log.d("time", updateDate.format(DateTimeFormatter.ofPattern("yy.MM.dd")))
            updateDate = updateDate.minusDays(1)
        }
        timeList = timeList.toSet().toMutableList()
        timeList.sort()


        Log.d("시간", timeList.size.toString())
    }

    fun setAlarm() {
        dailyAlertTimePreferenceHelper.getAlarmTime().also {
            dailyAlertManager.setDailyAlarm(it.hour, it.minute)
        }
    }
}