package com.hackaprende.dogedex.auth.di

import com.hackaprende.dogedex.auth.auth.AuthRepository
import com.hackaprende.dogedex.auth.auth.AuthTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthTasksModule {

    @Binds
    abstract fun bindDogTasks(
        authRepository: AuthRepository
    ): AuthTasks
}