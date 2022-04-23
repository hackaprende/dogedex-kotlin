package com.hackaprende.dogedex.core.di

import com.hackaprende.dogedex.core.doglist.DogRepository
import com.hackaprende.dogedex.core.doglist.DogTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DogTasksModule {

    @Binds
    abstract fun bindDogTasks(
        dogRepository: DogRepository
    ): DogTasks
}