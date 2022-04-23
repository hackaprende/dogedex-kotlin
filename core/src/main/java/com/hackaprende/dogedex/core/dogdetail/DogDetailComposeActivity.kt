package com.hackaprende.dogedex.core.dogdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import coil.annotation.ExperimentalCoilApi
import com.hackaprende.dogedex.core.dogdetail.ui.theme.DogedexTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalCoilApi
@AndroidEntryPoint
class DogDetailComposeActivity : ComponentActivity() {
    companion object {
        const val DOG_KEY = "dog"
        const val MOST_PROBABLE_DOGS_IDS = "most_probable_dogs_ids"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogedexTheme {
                DogDetailScreen(
                    finishActivity = { finish() }
                )
            }
        }
    }
}