package com.hackaprende.dogedex.core.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import coil.annotation.ExperimentalCoilApi
import com.hackaprende.dogedex.core.R
import com.hackaprende.dogedex.core.databinding.ActivitySettingsBinding
import com.hackaprende.dogedex.core.model.User

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        User.logout(this)
        try {
            val intent = Intent(
                this,
                Class.forName("com.hackaprende.dogedex.auth.auth.LoginActivity")
            )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            Toast.makeText(this, getString(R.string.login_screen_error), Toast.LENGTH_SHORT).show()
        }
        startActivity(intent)
    }
}