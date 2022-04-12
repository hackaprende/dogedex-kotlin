package com.hackaprende.dogedex.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.dogdetail.ui.theme.DogedexTheme
import com.hackaprende.dogedex.model.Dog
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalCoilApi
@AndroidEntryPoint
class DogDetailComposeActivity : ComponentActivity() {
    companion object {
        const val DOG_KEY = "dog"
        const val MOST_PROBABLE_DOGS_IDS = "most_probable_dogs_ids"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

    private val viewModel: DogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val probableDogsIds = intent?.extras?.getStringArrayList(MOST_PROBABLE_DOGS_IDS) ?: mutableListOf()
        val isRecognition = intent
            ?.extras
            ?.getBoolean(IS_RECOGNITION_KEY, false) ?: false

        if (dog == null) {
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            val status = viewModel.status
            if (status.value is ApiResponseStatus.Success) {
                finish()
            } else {
                DogedexTheme {
                    DogDetailScreen(
                        dog = dog,
                        status = status.value,
                        onButtonClicked = {
                            onButtonClicked(dog.id, isRecognition)
                        },
                        onErrorDialogDismiss = ::resetApiResponseStatus
                    )
                }
            }
        }
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    private fun onButtonClicked(dogId: Long, isRecognition: Boolean) {
        if (isRecognition) {
            viewModel.addDogToUser(dogId)
        } else {
            finish()
        }
    }
}