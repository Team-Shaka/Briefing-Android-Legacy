import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.briefing.R
import com.dev.briefing.presentation.theme.MainPrimary
import com.dev.briefing.presentation.theme.MainPrimary3
import com.dev.briefing.presentation.theme.Typography
import com.dev.briefing.presentation.theme.White
import java.time.format.DateTimeFormatter

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .background(color = MainPrimary)
            .padding(horizontal = 36.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = Typography.titleLarge.copy(
                fontSize = 70.sp,
                lineHeight = 85.sp,
                color = White
            )
        )
        Text(
            text = stringResource(R.string.login_app_description),
            style = Typography.titleMedium.copy(color = White)
        )
        Image(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.ic_gpt_logo), contentDescription = "fdfd"
        )
        GoogleLoginButton()
        Divider(modifier = Modifier.padding(vertical = 24.dp), color = MainPrimary3)
        Text(
            text = stringResource(R.string.login_skip),
            style = Typography.titleSmall.copy(color = MainPrimary)
        )
    }
}
@Composable
fun GoogleLoginButton(){
    Row(
        modifier = Modifier
            .background(color = White)
            .padding(vertical = 13.dp)
            .clickable {
                //TODO: 구글 로그인 로직
            }
    ){
        Image(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.ic_google_logo), contentDescription = "fdfd"
        )

        Text(
            text = "Log in with Google",
            style = Typography.headlineLarge.copy(color = Color(0xFF7C7C7C))
        )
    }
}