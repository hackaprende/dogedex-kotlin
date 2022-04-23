package com.hackaprende.dogedex.camera.di

import android.content.Context
import com.hackaprende.dogedex.camera.LABEL_PATH
import com.hackaprende.dogedex.camera.MODEL_PATH
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.tensorflow.lite.support.common.FileUtil
import java.nio.MappedByteBuffer

@Module
@InstallIn(SingletonComponent::class)
object ClassifierConstructorModule {

    @Provides
    fun providesClassifierModel(@ApplicationContext context: Context): MappedByteBuffer =
        FileUtil.loadMappedFile(context, MODEL_PATH)

    @Provides
    fun providesClassifierLabels(@ApplicationContext context: Context): List<String> =
        FileUtil.loadLabels(context, LABEL_PATH)
}