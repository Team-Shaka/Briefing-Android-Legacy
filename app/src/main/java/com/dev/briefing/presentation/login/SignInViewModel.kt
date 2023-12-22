package com.dev.briefing.presentation.login

import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.briefing.data.model.SocialLoginRequest
import com.dev.briefing.data.respository.AuthRepository
import com.dev.briefing.presentation.detail.ArticleDetailUiState
import com.dev.briefing.util.SERVER_TAG
import com.dev.briefing.util.preference.AuthPreferenceHelper
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: AuthRepository,
    private val authPreferenceHelper: AuthPreferenceHelper,
) : ViewModel() {

    private val _signInState =
        MutableStateFlow<SignInUiState>(SignInUiState.Default)

    val signInUiState: StateFlow<SignInUiState> =
        _signInState.asStateFlow()

    private fun handleGoogleIdToken(idToken: String) {
        _signInState.update { SignInUiState.Loading }

        viewModelScope.launch {
            runCatching {
                authRepository.signInWithSocialProvider(
                    "google",
                    SocialLoginRequest(
                        identityToken = idToken
                    )
                )
            }.onSuccess {
                it.result.let { socialLoginResponse ->
                    authPreferenceHelper.saveToken(
                        socialLoginResponse.accessToken,
                        socialLoginResponse.refreshToken
                    )
                    authPreferenceHelper.saveMemberId(socialLoginResponse.memberId)

                    Logger.d(socialLoginResponse)

                    _signInState.update { SignInUiState.Success }
                }
            }.onFailure {
                _signInState.update { SignInUiState.Error }
                Logger.e(it.message ?: "error in handleGoogleIdToken")
            }
        }
    }

    fun handleCredentialSignIn(result: GetCredentialResponse) {
        _signInState.update { SignInUiState.Loading }
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        handleGoogleIdToken(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        _signInState.update { SignInUiState.Error }
                        Logger.e(e.localizedMessage ?: "GoogleIdTokenParsingException")
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    _signInState.update { SignInUiState.Error }
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
            }
        }
    }

    fun withdrawal(memberId: Int) {
        viewModelScope.launch {
            try {
                val response = authRepository.signOut(memberId)
                Log.d(SERVER_TAG, response.code)
//                _statusMsg.value = response.message
            } catch (e: Throwable) {
                Log.d(SERVER_TAG, e.toString())
//                _statusMsg.value = e.toString()
            }
        }
    }
}
