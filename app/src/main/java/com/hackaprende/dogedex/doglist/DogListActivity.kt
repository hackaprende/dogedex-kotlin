package com.hackaprende.dogedex.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import coil.annotation.ExperimentalCoilApi
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.databinding.ActivityDogListBinding
import com.hackaprende.dogedex.dogdetail.DogDetailComposeActivity
import com.hackaprende.dogedex.dogdetail.ui.theme.DogedexTheme
import com.hackaprende.dogedex.model.Dog

private const val GRID_SPAN_COUNT = 3

@ExperimentalCoilApi
class DogListActivity : ComponentActivity() {

    private val viewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogedexTheme {
                val dogList = viewModel.dogList
                DogListScreen(
                    dogList = dogList.value,
                    onDogClicked = ::openDogDetailActivity
                )
            }
        }
    }

    private fun openDogDetailActivity(dog: Dog) {
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DogDetailComposeActivity.DOG_KEY, dog)
        startActivity(intent)
    }
}