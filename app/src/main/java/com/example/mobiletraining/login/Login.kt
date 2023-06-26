package com.example.mobiletraining.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firsttask.R
import com.example.mobiletraining.api.TokenProvider
import com.example.mobiletraining.models.UserRequest
import com.example.mobiletraining.models.UserResponse
import com.example.mobiletraining.models.viewmodels.UserViewModel
import com.example.mobiletraining.ui.theme.DisabledButton
import com.example.mobiletraining.ui.theme.Violet
import com.example.mobiletraining.ui.theme.White
import com.example.mobiletraining.utils.ToastMessage

@Composable
fun Login(tokenProvider: TokenProvider, loginHandler: () -> Unit = {}) {
    val ctx = LocalContext.current
    val loginViewModel: UserViewModel = hiltViewModel()
    val response by loginViewModel.response.collectAsState()
    val isLoading by loginViewModel.isLoading.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailIsValid by remember { mutableStateOf(true) }
    var passwordIsValid by remember { mutableStateOf(true) }

    LaunchedEffect(response) {
        response?.let { result ->
            result.onSuccess { response: UserResponse ->
                tokenProvider.setJwtToken(response.jwt)
                loginHandler()
            }
            result.onFailure {
                emailIsValid = false
                passwordIsValid = false
                ToastMessage.showToastMessage(ctx, it.message)
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.login__background),
            contentDescription = stringResource(id = R.string.BACKGROUND_IMAGE_DESCRIPTION),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.PADDING_MEDIUM))
                .padding(horizontal = dimensionResource(id = R.dimen.PADDING_MEDIUM_PLUS))
                .padding(top = dimensionResource(id = R.dimen.PADDING_LOGO_TOP)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                modifier = Modifier.padding(
                    bottom = dimensionResource(id = R.dimen.PADDING_LOGO_BOTTOM)
                ),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.LOGO_IMAGE_DESCRIPTION),
            )
            LoginTitle(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.PADDING_LOGIN_TITLE_TOP),
                        bottom = dimensionResource(id = R.dimen.PADDING_LARGE)
                    )
                    .padding(horizontal = dimensionResource(id = R.dimen.PADDING_LARGE))
            )

            LoginEmailTextField(
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.PADDING_LARGE))
                    .padding(horizontal = dimensionResource(id = R.dimen.PADDING_MEDIUM_PLUS)),
                email = email,
                setEmailValue = { email = it },
                isValidEmail = emailIsValid,
            )
            LoginPasswordTextField(
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.PADDING_MEDIUM_PLUS)),
                password = password,
                setPassword = { password = it },
                isValidPassword = passwordIsValid,
            )

            if (!emailIsValid || !passwordIsValid) {
                LoginErrorMessage(
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.PADDING_LARGE),
                        top = dimensionResource(id = R.dimen.PADDING_LARGE)
                    )
                )
            }

            LoginButton(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.PADDING_LOGIN_BUTTON_TOP))
                    .width(dimensionResource(id = R.dimen.LOGIN_BUTTON_WIDTH)),
                isLoading = isLoading,
                clickHandler = { loginViewModel.login(UserRequest(email, password)) }
            )
        }
    }
}

@Composable
private fun LoginTitle(modifier: Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(id = R.string.LOGIN_TEXT),
        style = TextStyle(
            fontSize = dimensionResource(id = R.dimen.FONT_SIZE_XXL).value.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Violet,
            textAlign = TextAlign.Left
        )
    )
}

@Composable
private fun LoginButton(
    modifier: Modifier,
    isLoading: Boolean,
    clickHandler: () -> Unit = {}
) {
    val customButtonColors = ButtonDefaults.buttonColors(
        containerColor = Violet,
        disabledContainerColor = DisabledButton,
    )
    Button(
        modifier = modifier,
        onClick = { clickHandler() },
        colors = customButtonColors,
        enabled = !isLoading,
    ) {
        Text(
            text = stringResource(id = R.string.LOGIN_TEXT),
            style = TextStyle(fontWeight = FontWeight.Bold),
        )
    }
}