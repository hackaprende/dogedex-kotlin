package com.hackaprende.dogedex.di

import com.hackaprende.dogedex.doglist.DogRepository
import com.hackaprende.dogedex.doglist.DogTasks
import com.hackaprende.dogedex.machinelearning.ClassifierRepository
import com.hackaprende.dogedex.machinelearning.ClassifierTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ClassifierModule {
    @Binds
    abstract fun bindClassifierTasks(
        classifierRepository: ClassifierRepository
    ): ClassifierTasks
}
