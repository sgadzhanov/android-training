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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(6.dp)
                .padding(horizontal = 8.dp)
                .padding(top = 68.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                modifier = Modifier.padding(bottom = 56.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
            )
            LoginTitle(
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 10.dp)
                    .padding(horizontal = 10.dp)
            )

            LoginEmailTextField(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .padding(horizontal = 8.dp),
                email = email,
                setEmailValue = { email = it },
                isValidEmail = emailIsValid,
                resetBorderColor = { emailIsValid = true },
            )
            LoginPasswordTextField(
                modifier = Modifier.padding(horizontal = 8.dp),
                password = password,
                setPassword = { password = it },
                isValidPassword = passwordIsValid,
                resetBorderColor = { passwordIsValid = true },
            )

            if (!emailIsValid || !passwordIsValid) {
                LoginErrorMessage(modifier = Modifier.padding(start = 10.dp, top = 10.dp))
            }

            LoginButton(
                modifier = Modifier
                    .padding(top = 35.dp)
                    .width(333.dp),
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
        text = "Log in",
        style = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF47337A),
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
        containerColor = Color(0xFF47337A),
        disabledContainerColor = Color(0xFF47337A),
    )
    Button(
        modifier = modifier,
        onClick = { clickHandler() },
        colors = customButtonColors,
        enabled = !isLoading,
    ) {
        if (isLoading) CircularProgressIndicator(color = Color.White, strokeWidth = 4.dp)
        else Text("Log in", style = TextStyle(fontWeight = FontWeight.Bold))
    }
}