package com.hackaprende.dogedex.core.di

import com.hackaprende.dogedex.core.auth.AuthRepository
import com.hackaprende.dogedex.core.auth.AuthTasks
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