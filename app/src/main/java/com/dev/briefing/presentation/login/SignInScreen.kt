import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dev.briefing.R
import com.dev.briefing.presentation.home.HomeActivity
import com.dev.briefing.presentation.login.SignInUiState
import com.dev.briefing.presentation.login.SignInViewModel
import com.dev.briefing.presentation.theme.BriefingTheme
import com.dev.briefing.presentation.theme.utils.CommonDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = koinViewModel(),
    requestGoogleSignIn: () -> Unit = {},
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
            confirmColor = BriefingTheme.color.PrimaryBlue,
            dismissText = R.string.dialog_basic_confirm
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = BriefingTheme.color.PrimaryBlue)
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
                style = BriefingTheme.typography.TitleStyleBold.copy(
                    fontSize = 70.sp,
                    lineHeight = 85.sp,
                    color = BriefingTheme.color.BackgroundWhite
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.login_app_description),
                style = BriefingTheme.typography.TitleStyleBold.copy(
                    color = BriefingTheme.color.BackgroundWhite,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
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
            requestGoogleSignIn()
        })
        HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp), color = Color(0xffb6b6b6))
        Text(
            modifier = Modifier.clickable(onClick = {
                openAlertDialog.value = true
            }),
            text = stringResource(R.string.login_skip),
            style = BriefingTheme.typography.SubtitleStyleBold.copy(color = Color.White)
        )
    }

    val _uiState = signInViewModel.signInUiState.collectAsStateWithLifecycle(SignInUiState.Default)

    when (val uiState = _uiState.value) {
        is SignInUiState.Success -> {
        }

        is SignInUiState.Error -> {

        }

        is SignInUiState.Default -> {
        }

        is SignInUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun GoogleLoginButton(
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BriefingTheme.color.BackgroundWhite,
                shape = RoundedCornerShape(25.dp)
            )
            .padding(vertical = 13.dp)
            .clickable(onClick = {
                onClick()
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
            style = BriefingTheme.typography.ContextStyleBold.copy(color = Color(0xFF7C7C7C))
        )
    }
}