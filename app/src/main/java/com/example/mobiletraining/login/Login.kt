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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firsttask.R
import com.example.mobiletraining.destinations.HomeDestination
import com.example.mobiletraining.models.viewmodels.UserViewModel
import com.example.mobiletraining.ui.theme.DisabledButton
import com.example.mobiletraining.ui.theme.Violet
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph
@NavGraph
annotation class LoginNavGraph(
    val start: Boolean = false
)

@LoginNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(destinationsNavigator: DestinationsNavigator) {
    val userViewModel: UserViewModel = hiltViewModel()
    val user = userViewModel.user
    var email by remember { mutableStateOf("test@test.com") }
    var password by remember { mutableStateOf("test123") }
    val emailIsValid by remember { mutableStateOf(true) }
    val passwordIsValid by remember { mutableStateOf(true) }

    LaunchedEffect(user) {
        user?.let { destinationsNavigator.navigate(HomeDestination) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.login__background),
            contentDescription = stringResource(id = R.string.background_image_description),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium_plus))
                .padding(top = dimensionResource(id = R.dimen.padding_logo_top)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                modifier = Modifier.padding(
                    bottom = dimensionResource(id = R.dimen.padding_logo_bottom)
                ),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo_image_description),
            )
            LoginTitle(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_login_title_top),
                        bottom = dimensionResource(id = R.dimen.padding_large)
                    )
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
            )

            LoginEmailTextField(
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.padding_large))
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_medium_plus)),
                email = email,
                setEmailValue = { email = it },
                isValidEmail = emailIsValid,
            )
            LoginPasswordTextField(
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium_plus)),
                password = password,
                setPassword = { password = it },
                isValidPassword = passwordIsValid,
            )

            if (!emailIsValid || !passwordIsValid) {
                LoginErrorMessage(
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.padding_large),
                        top = dimensionResource(id = R.dimen.padding_large)
                    )
                )
            }

            LoginButton(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.padding_login_button_top))
                    .width(dimensionResource(id = R.dimen.login_button_width)),
                isLoading = userViewModel.isLoading,
                clickHandler = { userViewModel.login(email, password) }
            )
        }
    }
}

@Composable
private fun LoginTitle(modifier: Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(id = R.string.login_text),
        style = TextStyle(
            fontSize = dimensionResource(id = R.dimen.font_size_xxl).value.sp,
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
            text = stringResource(id = R.string.login_text),
            style = TextStyle(fontWeight = FontWeight.Bold),
        )
    }
}