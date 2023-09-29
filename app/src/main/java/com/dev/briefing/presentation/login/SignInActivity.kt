package com.dev.briefing.presentation.login

import SignInScreen
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dev.briefing.BuildConfig
import com.dev.briefing.presentation.theme.BriefingTheme
import com.dev.briefing.util.RC_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class SignInActivity : ComponentActivity() {
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SignInActivity", "onCreate: ")
        setContent {
            BriefingTheme {
                // A surface container using the 'background' color from the theme
                SignInScreen(
                    googelSignIn = { googelSignIn() }
                )
            }
        }
    }

    fun googelSignIn() {
        //1. 앱에 필요한 사용자 데이터를 요청하도록 로그인 옵션을 설정해줌
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_API_KEY)
            .requestEmail()//email 요청
            .build()
        //2. 위에서 만든 gso를 파라미터로 넣어줘서 googlesignInClient 객체를 만들어줌
        val mGoogleSignInClient = GoogleSignIn.getClient(this@SignInActivity, gso)
        //3. signIn할수있는 intent를 생성
        val signInIntent = mGoogleSignInClient.signInIntent
        //4. startActivityForResult를 통해 구글 로그인 창을 띄움
        val googleCallBack =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RC_SIGN_IN) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    handleSignInResult(task)
                }
            }
        googleCallBack.launch(signInIntent)

    }

    //5. 사용자 정보 가져오기
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            Log.d("Google", completedTask.isSuccessful.toString())
            val account = completedTask.getResult(ApiException::class.java)

            Log.d("Google", account.account.toString())
            Log.d("Google", account.displayName.toString())
            Log.d("Google", account.idToken.toString())
            Log.d("Google", account.id.toString())
            account.idToken?.let {
                Log.d("Google", it)
            } ?: Toast.makeText(this, "google 로그인에 실패하였습니다. 다시시도해주세요", Toast.LENGTH_SHORT).show()

            // Signed in successfully, show authenticated UI.
//            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("Google", "google 로그인에 실패하였습니다. 다시시도해주세요${e}")

        }
    }
}
