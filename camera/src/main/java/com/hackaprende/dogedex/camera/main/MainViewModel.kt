package com.hackaprende.dogedex.camera.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackaprende.dogedex.core.api.ApiResponseStatus
import com.hackaprende.dogedex.camera.machinelearning.ClassifierTasks
import com.hackaprende.dogedex.camera.machinelearning.DogRecognition
import com.hackaprende.dogedex.core.doglist.DogTasks
import com.hackaprende.dogedex.core.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dogRepository: DogTasks,
    private val classifierRepository: ClassifierTasks,
): ViewModel() {

    private val _dog = MutableLiveData<Dog>()
    val dog: LiveData<Dog>
        get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>>
        get() = _status

    private val _dogRecognition = MutableLiveData<DogRecognition>()
    val dogRecognition: LiveData<DogRecognition>
        get() = _dogRecognition

    val probableDogIds = mutableListOf<String>()

    fun recognizeImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            val dogRecognitionList = classifierRepository.recognizeImage(imageProxy)
            updateDogRecognition(dogRecognitionList)
            updateProbableDogIds(dogRecognitionList)
            imageProxy.close()
        }
    }

    private fun updateProbableDogIds(dogRecognitionList: List<DogRecognition>) {
        probableDogIds.clear()
        if (dogRecognitionList.size >= 5) {
            probableDogIds
                .addAll(dogRecognitionList.subList(1, 4)
                    .map {
                        it.id
                    })
        }
    }

    private fun updateDogRecognition(dogRecognitionList: List<DogRecognition>) {
        _dogRecognition.value = dogRecognitionList.first()
    }

    fun getDogByMlId(mlDogId: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.getDogByMlId(mlDogId))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<Dog>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _dog.value = apiResponseStatus.data!!
        }

        _status.value = apiResponseStatus
    }

}