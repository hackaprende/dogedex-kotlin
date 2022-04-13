package com.hackaprende.dogedex.dogdetail

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.annotation.ExperimentalCoilApi
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.doglist.DogTasks
import com.hackaprende.dogedex.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoilApi
@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val dogRepository: DogTasks,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    var dog = mutableStateOf(
        savedStateHandle.get<Dog>(DogDetailComposeActivity.DOG_KEY)
    )
        private set

    var probableDogsIds = mutableStateListOf(
        savedStateHandle.get<ArrayList<String>>(DogDetailComposeActivity.MOST_PROBABLE_DOGS_IDS)
    )
        private set

    var isRecognition = mutableStateOf(
        savedStateHandle.get<Boolean>(DogDetailComposeActivity.MOST_PROBABLE_DOGS_IDS) ?: false
    )
        private set

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    fun addDogToUser() {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dog.value!!.id))
        }
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        status.value = apiResponseStatus
    }

    fun resetApiResponseStatus() {
        status.value = null
    }
}