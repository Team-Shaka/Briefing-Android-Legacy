import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.dev.briefing.R
import com.dev.briefing.presentation.home.HomeActivity
import com.dev.briefing.presentation.home.HomeViewModel
import com.dev.briefing.presentation.login.SignInViewModel
import com.dev.briefing.presentation.login.mGoogleSignInClient
import com.dev.briefing.presentation.theme.MainPrimary
import com.dev.briefing.presentation.theme.MainPrimary2
import com.dev.briefing.presentation.theme.MainPrimary3
import com.dev.briefing.presentation.theme.MainPrimary5
import com.dev.briefing.presentation.theme.Typography
import com.dev.briefing.presentation.theme.White
import com.dev.briefing.presentation.theme.utils.CommonDialog
import com.dev.briefing.util.JWT_TOKEN
import com.dev.briefing.util.MainApplication.Companion.prefs
import com.google.android.gms.auth.api.Auth.GoogleSignInApi
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.koin.androidx.compose.getViewModel

@Composable
fun SignInScreen(
    googelSignIn: () -> Unit = {}
) {
    val context = LocalContext.current
    val openAlertDialog = remember { mutableStateOf(false) }
    if (openAlertDialog.value) {
        CommonDialog(onDismissRequest = {
            openAlertDialog.value = false
            startActivity(context, Intent(context, HomeActivity::class.java), null)
        },
            onConfirmation = { openAlertDialog.value = false },
            dialogTitle = R.string.dialog_login_skip_title,
            dialogText = R.string.dialog_login_skip_text,
            dialogId = R.string.dialog_login_skip_confirm,
            confirmColor = MainPrimary2,
            dismissText = R.string.dialog_basic_confirm
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MainPrimary)
            .padding(horizontal = 36.dp, vertical = 60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_name), style = Typography.titleLarge.copy(
                    fontSize = 70.sp, lineHeight = 85.sp, color = White
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.login_app_description),
                style = Typography.titleMedium.copy(color = White, fontWeight = FontWeight.Normal)
            )
            Spacer(modifier = Modifier.height(19.dp))
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_gpt_logo),
                contentDescription = "fdfd"
            )
        }
        Spacer(modifier = Modifier.weight(2f))
        GoogleLoginButton(onClick = {
            googelSignIn()
        }

        )
        Divider(modifier = Modifier.padding(vertical = 24.dp), color = MainPrimary3)
        Text(
            modifier = Modifier.clickable(onClick = {
                openAlertDialog.value = true
            }),
            text = stringResource(R.string.login_skip),
            style = Typography.titleSmall.copy(color = MainPrimary5)
        )
    }
}

@Composable
fun GoogleLoginButton(
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current

    val singinViewModel: SignInViewModel = getViewModel()
    var idToken: String = ""

    var vieModelResult = singinViewModel.result.observeAsState(false)

    //4. startActivityForResult callback 함수를 통해 구글 로그인 창을 띄움
    val googleCallBack =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            // Intent 타입으로 받은 data를 GoogleSignInResult로 바꿔준다.
            var rs = result.data?.let { GoogleSignInApi.getSignInResultFromIntent(it) }
            Log.e("RESULT", rs?.status.toString())
            Log.d("Google", "2: callback 호출")
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    Log.d("Google", "3: oAuth 동의화면 출력")
                    idToken = handleSignInResult(task)?.let { it } ?: ""
                    idToken?.let {
                        Log.d("Google", "6: ${idToken} handleSignInResult로 정보 받기완료")
                        singinViewModel.getLoginCode(it)
                    }


                } else {
                    Toast.makeText(context, "구글 로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "구글 로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    LaunchedEffect(key1 = vieModelResult.value) {
        Log.d("Google", "(상시호출) ${singinViewModel.result.value}")

        if (singinViewModel.result.value == true) {
            Log.d("Google", "11 ${singinViewModel.result.value}")
            val intent = Intent(context, HomeActivity::class.java)
            startActivity(context, intent, null)
            val activity = context as? ComponentActivity
            activity?.finish()
        } else {
            if (singinViewModel.statusMsg.value != "") {
                Toast.makeText(context, singinViewModel.statusMsg.value, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(25.dp))
            .padding(vertical = 13.dp)
            .clickable(onClick = {
                onClick()
                googleCallBack.launch(mGoogleSignInClient?.signInIntent)
            }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = "fdfd"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Log in with Google",
            style = Typography.headlineLarge.copy(color = Color(0xFF7C7C7C))
        )
    }
}

//5. 사용자 정보 가져오기
fun handleSignInResult(completedTask: Task<GoogleSignInAccount>): String? {
    try {
        Log.d("Google", "4: task receive;${completedTask.isSuccessful} ")
        val account = completedTask.getResult(ApiException::class.java)
        //idToken을 반환시 Log 출력
        account.idToken?.let {
            Log.d("Google", "5: idToken 정보 받기 완료")
        } ?: Log.d("Google", "5: 구글 로그인 에러")
        return account.idToken.toString()
    } catch (e: ApiException) {
        Log.d("Google", "google 로그인에 실패하였습니다. 다시시도해주세요${e}")
    }
    return null
}
