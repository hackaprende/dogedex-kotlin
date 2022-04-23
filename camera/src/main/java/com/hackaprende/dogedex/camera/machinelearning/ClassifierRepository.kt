package com.hackaprende.dogedex.camera.machinelearning

import android.annotation.SuppressLint
import android.graphics.*
import androidx.camera.core.ImageProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

interface ClassifierTasks {
    suspend fun recognizeImage(imageProxy: ImageProxy): List<DogRecognition>
}

class ClassifierRepository @Inject constructor(private val classifier: Classifier) :
    ClassifierTasks {

    override suspend fun recognizeImage(imageProxy: ImageProxy): List<DogRecognition> =
        withContext(Dispatchers.IO) {
            val bitmap = convertImageProxyToBitmap(imageProxy)
            if (bitmap == null) {
                listOf(DogRecognition("", 0f))
            } else {
                classifier.recognizeImage(bitmap).subList(0, 5)
            }
        }

    @SuppressLint("UnsafeOptInUsageError")
    private fun convertImageProxyToBitmap(imageProxy: ImageProxy): Bitmap? {
        val image = imageProxy.image ?: return null

        val yBuffer = image.planes[0].buffer // Y
        val uBuffer = image.planes[1].buffer // U
        val vBuffer = image.planes[2].buffer // V

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        //U and V are swapped
        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(
            Rect(0, 0, yuvImage.width, yuvImage.height), 100,
            out
        )
        val imageBytes = out.toByteArray()

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}