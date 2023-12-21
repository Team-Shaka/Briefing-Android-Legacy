package com.dev.briefing.presentation.login

import SignInScreen
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.dev.briefing.BuildConfig
import com.dev.briefing.R
import com.dev.briefing.presentation.home.HomeActivity
import com.dev.briefing.presentation.theme.BriefingTheme
import com.dev.briefing.util.RC_SIGN_IN
import com.dev.briefing.util.preference.AuthPreferenceHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

var mGoogleSignInClient: GoogleSignInClient? = null

class SignInActivity : ComponentActivity() {
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
                    googelSignIn = { googelSignIn() }
                )
            }
        }

    }

    private fun googelSignIn(): GoogleSignInClient {
        //1. 앱에 필요한 사용자 데이터를 요청하도록 로그인 옵션을 설정해줌
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)//id token 요청
            .requestEmail()//email 요청
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        Log.d("Google", "1: get client${mGoogleSignInClient}")
        return mGoogleSignInClient!!
    }

}
