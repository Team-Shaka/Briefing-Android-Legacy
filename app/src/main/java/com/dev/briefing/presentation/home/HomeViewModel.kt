package com.dev.briefing.presentation.home

import android.util.Log
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.data.respository.BriefingRepository
import com.dev.briefing.util.MOCK_DATE
import com.dev.briefing.util.SERVER_TAG
import com.dev.briefing.util.UPDATE_DATE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel(private val repository:BriefingRepository):ViewModel() {

    private val _serverTestResponse: MutableLiveData<BriefingResponse> = MutableLiveData<BriefingResponse>()
    val serverTestResponse: LiveData<BriefingResponse>
        get() = _serverTestResponse



    //time관련 변수
    var timeList: MutableList<LocalDate> = mutableListOf()
    val today: LocalDate = LocalDate.now()
    //TODO: time add start 출시일 ~ end 오늘날

    // 변경 가능한 LiveData를 선언
    private var _briefDate = MutableLiveData<LocalDate>(today)
    // 외부로 불변성을 유지하기 위해 공개하는 LiveData
    val briefDate: LiveData<LocalDate> = _briefDate
    var briefText = "오늘"

    init {
        getBriefingDataApi(today)
        setDateList()
    }

    fun getBriefingDataApi(date:LocalDate){
        viewModelScope.launch {
            try {
                val response = repository.getBriefingKeyword(
                    briefingDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    type = "Korea")
                if(response.briefings != null) {
                    _serverTestResponse.value = response
                } else {
                }
            } catch (e: Throwable) {
                Log.d(SERVER_TAG,e.toString())
            }

            Log.d(SERVER_TAG,"끝!")
        }

    }
    //click 이벤트가 발생하면 호출되는 함수
    fun changeBriefDate(date: LocalDate){
        _briefDate.value = date
        getBriefingDataApi(date)
        briefText = if (_briefDate.value == today) {
            "오늘"
        } else {
            "그날"
        }
    }
    fun setBriefText(){
        briefText = if (briefDate.value == today) {
            "오늘"
        } else {
            "그날"
        }
    }
    fun setDateList(){
        setBriefText()

        var updateDate:LocalDate = today.minusDays(1)
        timeList.add(today)
        while (timeList.size <7 && updateDate.isAfter(UPDATE_DATE)) {
            timeList.add(updateDate)
            Log.d("time", updateDate.format(DateTimeFormatter.ofPattern("yy.MM.dd")))
            updateDate = updateDate.minusDays(1)
        }
        if(timeList.size!=7)timeList.add(UPDATE_DATE)
        timeList = timeList.toSet().toMutableList()
        timeList.sort()
        //UI를 위한  조건에 벗어나는 날짜 2개 더함
        //TODO: 하드코딩 제거
        when(timeList.size){
            1-> {
                timeList.add(0,MOCK_DATE)
                timeList.add(0,MOCK_DATE)
                timeList.add(MOCK_DATE)
                timeList.add(MOCK_DATE)
            }
            2-> {
                timeList.add(0,MOCK_DATE)
                timeList.add(MOCK_DATE)
                timeList.add(MOCK_DATE)
            }
            else->{
                timeList.add(MOCK_DATE)
                timeList.add(MOCK_DATE)
            }
        }
       
        Log.d("시간",timeList.size.toString())
    }


}