package com.hackaprende.dogedex

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

class DogListViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var dogedexCoroutineRule = DogedexCoroutineRule()
}