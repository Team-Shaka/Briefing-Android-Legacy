package com.dev.briefing.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.model.GoogleRequest
import com.dev.briefing.data.respository.AuthRepository
import com.dev.briefing.util.SERVER_TAG
import com.dev.briefing.util.preference.AuthPreferenceHelper
import kotlinx.coroutines.launch

class SignInViewModel(
    private val repository: AuthRepository,
    private val authPreferenceHelper: AuthPreferenceHelper
) : ViewModel() {

    private val _accessToken: MutableLiveData<String> = MutableLiveData<String>()
    val accessToken: LiveData<String>
        get() = _accessToken

    private val _statusMsg: MutableLiveData<String> = MutableLiveData<String>("")
    val statusMsg: LiveData<String>
        get() = _statusMsg

    private val _result: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val result: LiveData<Boolean>
        get() = _result

    fun getLoginCode(idToken: String) {
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
                authPreferenceHelper.saveMemberId(response.result.memberId)
                authPreferenceHelper.saveToken(
                    response.result.accessToken,
                    response.result.refreshToken
                )

                Log.d("Google", "8: ${authPreferenceHelper.getMemberId()} SharePreference 저장완료")
                _statusMsg.value = response.message
                _result.value = true
                Log.d("Google", "9: api 호출 완료 _result.value = ${_result.value}")

            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
                _statusMsg.value = e.toString()
                _result.value = false
            }
        }
        Log.d("Google", "10: coroutine 끝")

    }

    fun withdrawal(memberId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.signOut(memberId)
                Log.d(SERVER_TAG, response.code)
                _statusMsg.value = response.message
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
                _statusMsg.value = e.toString()
            }
        }
    }
}
