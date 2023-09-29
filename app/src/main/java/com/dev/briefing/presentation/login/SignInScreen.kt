import android.content.Intent
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.dev.briefing.BuildConfig.GOOGLE_API_KEY
import com.dev.briefing.R
import com.dev.briefing.presentation.home.HomeActivity
import com.dev.briefing.presentation.theme.MainPrimary
import com.dev.briefing.presentation.theme.MainPrimary2
import com.dev.briefing.presentation.theme.MainPrimary3
import com.dev.briefing.presentation.theme.MainPrimary5
import com.dev.briefing.presentation.theme.Typography
import com.dev.briefing.presentation.theme.White
import com.dev.briefing.presentation.theme.utils.CommonDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun SignInScreen(
    googelSignIn:() -> Unit = {}
) {
    val context = LocalContext.current
    val openAlertDialog = remember { mutableStateOf(false) }
    if (openAlertDialog.value) {
        CommonDialog(
            onDismissRequest = {
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
                text = stringResource(R.string.app_name),
                style = Typography.titleLarge.copy(
                    fontSize = 70.sp,
                    lineHeight = 85.sp,
                    color = White
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.login_app_description),
                style = Typography.titleMedium.copy(color = White)
            )
            Spacer(modifier = Modifier.height(19.dp))
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_gpt_logo), contentDescription = "fdfd"
            )
        }
        Spacer(modifier = Modifier.weight(2f))
        GoogleLoginButton(
            onClick = {
                googelSignIn()
                //startActivity(context, Intent(context, HomeActivity::class.java), null)
            }
        )
        Divider(modifier = Modifier.padding(vertical = 24.dp), color = MainPrimary3)
        Text(
            modifier = Modifier.clickable(onClick = {
                openAlertDialog.value = true
//                startActivity(context, Intent(context, HomeActivity::class.java), null)
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(25.dp))
            .padding(vertical = 13.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.ic_google_logo), contentDescription = "fdfd"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Log in with Google",
            style = Typography.headlineLarge.copy(color = Color(0xFF7C7C7C))
        )
    }
}