package com.dev.briefing.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import com.dev.briefing.data.respository.AuthRepository
import com.dev.briefing.data.respository.BriefingRepository
import com.dev.briefing.data.respository.BriefingRepositoryImpl
import com.dev.briefing.util.JWT_TOKEN
import com.dev.briefing.util.MEMBER_ID
import com.dev.briefing.util.MainApplication
import com.dev.briefing.util.MainApplication.Companion.prefs
import com.dev.briefing.util.REFRESH_TOKEN
import com.dev.briefing.util.SERVER_TAG
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SignInViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _accessToken: MutableLiveData<String> = MutableLiveData<String>()
    val accessToken: LiveData<String>
        get() = _accessToken

    private val _statusMsg: MutableLiveData<String> = MutableLiveData<String>("")
    val statusMsg: LiveData<String>
        get() = _statusMsg

    private val _result:MutableLiveData<Boolean>  = MutableLiveData<Boolean>()
    val result: LiveData<Boolean>
        get() = _result
    fun getLoginCode(idToken: String){
        viewModelScope.launch {
            try {
                val response = repository.getLoginToken(
                    GoogleRequest(
                        identityToken = idToken
                    )
                )
                Log.d("Google", "7: ${response.message} 서버 통신완료")
                _accessToken.value = response.result.accessToken
                //save preference
                prefs.putSharedPreference(JWT_TOKEN,response.result.accessToken)
                prefs.putSharedPreference(REFRESH_TOKEN,response.result.refreshToken)
                prefs.putSharedPreference(MEMBER_ID,response.result.memberId)
                Log.d("Google", "8: ${prefs.getSharedPreference(MEMBER_ID,-1)} SharePreference 저장완료")
                _statusMsg.value = response.message
                _result.value= true
                Log.d("Google", "9: api 호출 완료 _result.value = ${_result.value}")

            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
                _statusMsg.value = e.toString()
                _result.value = false
            }
        }
        Log.d("Google", "10: coroutine 끝")

    }
    fun signout(memberId:Int) {
        viewModelScope.launch {
            try {
                val response = repository.signOut(memberId)
                Log.d(SERVER_TAG, response.code)
                _statusMsg.value = response.message
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
                _statusMsg.value = e.toString()
                getAcessToken(prefs.getSharedPreference(REFRESH_TOKEN,""))
//                signout(memberId)
            }
        }
    }

    fun getAcessToken(refreshToken:String) {
        viewModelScope.launch {
            try {
                val response = repository.getAccessToken(
                    com.dev.briefing.data.model.TokenRequest(
                        refreshToken = refreshToken
                    )
                )
                Log.d(SERVER_TAG, response.code)
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
            }
        }
    }

}
