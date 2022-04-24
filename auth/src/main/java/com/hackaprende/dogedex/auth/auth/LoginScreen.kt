package com.hackaprende.dogedex.auth.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hackaprende.dogedex.core.R
import com.hackaprende.dogedex.core.composables.AuthField

@Composable
fun LoginScreen(
    onLoginButtonClick: (String, String) -> Unit,
    onRegisterButtonClick: () -> Unit,
    authViewModel: AuthViewModel,
) {
    Scaffold(
        topBar = { LoginScreenToolbar() }
    ) {
        Content(
            onLoginButtonClick = onLoginButtonClick,
            onRegisterButtonClick = {
                onRegisterButtonClick()
                authViewModel.resetErrors()
            },
            resetFieldErrors = { authViewModel.resetErrors() },
            authViewModel = authViewModel
        )
    }
}

@Composable
private fun Content(
    onLoginButtonClick: (String, String) -> Unit,
    onRegisterButtonClick: () -> Unit,
    resetFieldErrors: () -> Unit,
    authViewModel: AuthViewModel,
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthField(
            label = stringResource(id = R.string.email),
            modifier = Modifier
                .fillMaxWidth(),
            email = email.value,
            onTextChanged = {
                email.value = it
                resetFieldErrors()
            },
            errorMessageId = authViewModel.emailError.value,
            errorSemantic = "email-field-error",
            fieldSemantic = "email-field",
        )

        AuthField(
            label = stringResource(id = R.string.password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            email = password.value,
            onTextChanged = {
                password.value = it
                resetFieldErrors()
            },
            visualTransformation = PasswordVisualTransformation(),
            errorMessageId = authViewModel.passwordError.value,
            errorSemantic = "password-field-error",
            fieldSemantic = "password-field",
        )

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .semantics { testTag = "login-button" },
            onClick = { onLoginButtonClick(email.value, password.value) }) {
            Text(
                stringResource(R.string.login),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.do_not_have_an_account)
        )

        Text(
            modifier = Modifier
                .clickable(enabled = true, onClick = { onRegisterButtonClick() })
                .fillMaxWidth()
                .padding(16.dp)
                .semantics { testTag = "login-screen-register-button" },
            text = stringResource(R.string.register),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun LoginScreenToolbar() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = Color.Red,
        contentColor = Color.White,
    )
}