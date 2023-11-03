package com.dev.briefing.presentation.detail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.NewsContent
import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.respository.BriefingRepository
import com.dev.briefing.util.SERVER_TAG
import com.dev.briefing.util.preference.SharedPreferenceHelper
import kotlinx.coroutines.launch

class ArticleDetailViewModel(private val repository: BriefingRepository,private val id: Int):ViewModel() {
    private val _detailPage: MutableLiveData<BriefingDetailResponse> = MutableLiveData<BriefingDetailResponse>()
    val detailPage: LiveData<BriefingDetailResponse>
        get() = _detailPage
    private val _isScrap:MutableLiveData<Boolean> = MutableLiveData()
    val isScrap : LiveData<Boolean>
        get() = _isScrap
    init{
        getBrieingId(id)
    }
    fun getBrieingId(id:Int){
        viewModelScope.launch {
            try {
                val response = repository.getBriefingDetail(
                    id = id)
                if(response != null) {
                    _detailPage.value = response
                } else {
                }
            } catch (e: Throwable) {
                Log.d(SERVER_TAG,e.toString())
            }

            Log.d(SERVER_TAG,"끝!")
        }

    }
    fun getScrapStatus(context: Context){
        val existingMap = SharedPreferenceHelper.loadDateIdMap(context)
        val existingIdList = existingMap[detailPage.value?.date]?.toMutableList() ?: mutableListOf()
        _isScrap.value =  detailPage.value?.id in existingIdList
        Log.d("스크랩","${detailPage.value?.title}은"+isScrap.toString())
        // 이미지 리소스를 불러옵니다.
    }
    fun setScrapStatus(context: Context){
        if (_isScrap.value == false) {
            //Scrap을 한다
            SharedPreferenceHelper.addIntToKey(context, detailPage.value?.date ?: "", detailPage.value?.id ?: 1)
            SharedPreferenceHelper.addArticleDetail(
                context, detailPage.value?.id?:0,
                NewsContent(rank = detailPage.value?.rank?:0, title = detailPage.value?.title?:"", subtitle = detailPage.value?.subtitle?:"")
            )
            Toast.makeText(context,"해당 브리핑을 스크랩 하였습니다. 스크랩은 보관함에서 모아볼 수 있습니다.", Toast.LENGTH_LONG).show()
            _isScrap.value =true
        } else {
            //TODO:Scrap을 취소한다
            SharedPreferenceHelper.removeIntFromKey(context, detailPage.value?.date?:"", detailPage.value?.id?:0)
            SharedPreferenceHelper.removeDetilFromId(context, detailPage.value?.id?:0)
            _isScrap.value = false
        }

    }

}