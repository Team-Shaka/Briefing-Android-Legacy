package com.dev.briefing.presentation.login

import SignInScreen
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dev.briefing.BuildConfig
import com.dev.briefing.presentation.home.HomeActivity
import com.dev.briefing.presentation.theme.BriefingTheme
import com.dev.briefing.util.preference.AuthPreferenceHelper
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject


class SignInActivity : ComponentActivity() {

    private val signInViewModel: SignInViewModel by inject(SignInViewModel::class.java)

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authPreferenceHelper = AuthPreferenceHelper(this)
        Log.d("SignInActivity", "onCreate: ")
        val token = authPreferenceHelper.getAccessToken()
        val memberId = authPreferenceHelper.getMemberId()
        if (token != "" && memberId != -1) {
            Toast.makeText(this, "자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(intent, null)
            finish()
        }

        setContent {
            BriefingTheme {
                // A surface container using the 'background' color from the theme
                SignInScreen(
                    requestGoogleSignIn = {
                        CoroutineScope(Dispatchers.Main).launch {
                            runCatching {
                                val getCredentialResponse = getCredentialWithGoogleSignIn()
                                signInViewModel.handleCredentialSignIn(getCredentialResponse)
                            }
                        }
                    }
                )
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signInViewModel.signInUiState.collect { uiState ->
                    when (uiState) {
                        SignInUiState.Success -> {
                            Toast.makeText(this@SignInActivity, "로그인 성공", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        else -> {}
                    }
                }
            }
        }

    }

    private suspend fun getCredentialWithGoogleSignIn(): GetCredentialResponse {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
            .build()

        val credentialManager = CredentialManager.create(this)

        val request: GetCredentialRequest =
            GetCredentialRequest.Builder().addCredentialOption(googleIdOption)
                .build()

        val result = credentialManager.getCredential(
            request = request,
            context = this@SignInActivity,
        )
        return result;
    }
}
