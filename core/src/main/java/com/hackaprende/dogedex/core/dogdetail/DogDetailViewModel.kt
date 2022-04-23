package com.hackaprende.dogedex.core.dogdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.annotation.ExperimentalCoilApi
import com.hackaprende.dogedex.core.api.ApiResponseStatus
import com.hackaprende.dogedex.core.doglist.DogTasks
import com.hackaprende.dogedex.core.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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

    private var probableDogsIds = mutableStateOf(
        savedStateHandle.get<ArrayList<String>>(DogDetailComposeActivity.MOST_PROBABLE_DOGS_IDS) ?: arrayListOf()
    )

    var isRecognition = mutableStateOf(
        savedStateHandle.get<Boolean>(DogDetailComposeActivity.IS_RECOGNITION_KEY) ?: false
    )
        private set

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    private var _probableDogList = MutableStateFlow<MutableList<Dog>>(mutableListOf())
    val probableDogList: StateFlow<MutableList<Dog>>
        get() = _probableDogList

    fun getProbableDogs() {
        _probableDogList.value.clear()
        viewModelScope.launch {
            dogRepository.getProbableDogs(probableDogsIds.value)
                .collect { apiResponseStatus ->
                    if (apiResponseStatus is ApiResponseStatus.Success) {
                        val probableDogMutableList = _probableDogList.value.toMutableList()
                        probableDogMutableList.add(apiResponseStatus.data)
                        _probableDogList.value = probableDogMutableList
                    }
                }
        }
    }

    fun updateDog(newDog: Dog) {
        dog.value = newDog
    }

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