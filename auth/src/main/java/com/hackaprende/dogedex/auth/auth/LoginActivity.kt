package com.hackaprende.dogedex.auth.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import coil.annotation.ExperimentalCoilApi
import com.hackaprende.dogedex.core.dogdetail.ui.theme.DogedexTheme
import com.hackaprende.dogedex.core.model.User
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogedexTheme {
                AuthScreen(
                    onUserLoggedIn = ::startMainActivity,
                )
            }
        }
    }

    private fun startMainActivity(userValue: User) {
        User.setLoggedInUser(this, userValue)
        try {
            startActivity(
                Intent(
                    this,
                    Class.forName("com.hackaprende.dogedex.camera.main.MainActivity")
                )
            )
        } catch (e: ClassNotFoundException) {
            Toast.makeText(this, "Camera Screen error", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}