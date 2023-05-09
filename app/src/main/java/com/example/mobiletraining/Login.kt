package com.example.mobiletraining


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firsttask.EMAIL
import com.example.firsttask.PASSWORD

@Composable
fun Login(loginHandler: () -> Unit = {}) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (emailBorder, setEmailBorder) = remember { mutableStateOf(Color.Gray) }
    val (passwordBorder, setPasswordBorder) = remember { mutableStateOf(Color.Gray) }

    Column(
        modifier = Modifier
            .padding(6.dp)

    ) {
        LoginTitle()
        EmailTextField(
            email = email,
            setEmailValue = { setEmail(it) },
            borderColor = emailBorder,
            resetBorderColor = { setEmailBorder(Color.Gray) }
        )
        PasswordTextField(
            password = password,
            setPassword = { setPassword(it) },
            borderColor = passwordBorder,
            resetBorderColor = { setPasswordBorder(Color.Gray) }
        )

        ErrorMessage(
            emailIsValid = emailBorder == Color.Gray,
            passwordIsValid = passwordBorder == Color.Gray
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = emailBorder == Color.Gray && passwordBorder == Color.Gray,
            onClick = {
                if (isValidPassword(password) && isValidEmail(email)) {
                    loginHandler()
                }
                setEmailBorder(if (isValidEmail(email)) Color.Gray else Color.Red)
                setPasswordBorder(if (isValidPassword(password)) Color.Gray else Color.Red)
                println("email: $email, password: $password")
                setEmail("")
                setPassword("")
            }) {
            Text("Log in", style = TextStyle(fontWeight = FontWeight.Bold))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmailTextField(
    email: String,
    setEmailValue: (String) -> Unit,
    borderColor: Color,
    resetBorderColor: (Color) -> Unit,
) {

    TextField(
        value = email,
        onValueChange = {
            resetBorderColor(Color.Gray)
            setEmailValue(it)
        },
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .border(
                1.dp,
                color = borderColor,
                shape = RoundedCornerShape(6.dp)
            ),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    password: String,
    setPassword: (String) -> Unit,
    borderColor: Color,
    resetBorderColor: (Color) -> Unit,
) {
    val (isVisible, setIsVisible) = remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = {
            resetBorderColor(Color.Gray)
            setPassword(it)
        },
        label = { Text("Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .border(
                1.dp,
                color = borderColor,
                shape = RoundedCornerShape(6.dp)
            ),
        textStyle = TextStyle(fontSize = 16.sp),
        singleLine = true,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { setIsVisible(!isVisible) }) {
                val icon: ImageVector =
                    if (isVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
                Icon(icon, contentDescription = "tease password")
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun LoginTitle() {
    Spacer(modifier = Modifier.height(100.dp))

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            text = "Log in",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun ErrorMessage(
    emailIsValid: Boolean,
    passwordIsValid: Boolean,
) {
    if (!emailIsValid || !passwordIsValid) {
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                text = "Please enter valid email and password.",
                style = TextStyle(color = Color.Red, fontSize = 18.sp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

private fun isValidEmail(email: String): Boolean {
//    val regex = Patterns.EMAIL_ADDRESS
//    return regex.matcher(email).matches()
    return email == EMAIL
}

private fun isValidPassword(password: String): Boolean {
    return password == PASSWORD
//    return password.trim().isNotEmpty() && password.length >= 6
}