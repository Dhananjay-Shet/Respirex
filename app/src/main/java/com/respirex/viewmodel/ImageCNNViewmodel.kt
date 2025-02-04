package com.respirex.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.respirex.data.ReportRepository
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class ImageCNNViewmodel: ViewModel() {

    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri> get()= _imageUri

    var imageUriOnly: Uri? =null

    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
        imageUriOnly = uri
    }

    private val _result = MutableLiveData<String>("")
    val result: LiveData<String> = _result

    val disease= ReportRepository.getDisease()

    fun generateResult(context: Context){
        val bitmap = loadImage(context,imageUriOnly!!)
        val preprocessedBuffer = preprocessImage(bitmap)
        val model = loadModelFile(context,disease)
        val interpreter = Interpreter(model)
        runModel(preprocessedBuffer, interpreter)
        interpreter.close()
    }

    fun loadImage(context: Context, uri: Uri): Bitmap {
        val contentResolver: ContentResolver = context.contentResolver
        val source = ImageDecoder.createSource(contentResolver, uri)
        val decodedBitmap = ImageDecoder.decodeBitmap(source)

        // Convert to mutable bitmap if it's hardware-configured
        if (decodedBitmap.config == Bitmap.Config.HARDWARE) {
            return decodedBitmap.copy(Bitmap.Config.ARGB_8888, true)
        }
        return decodedBitmap
    }

    private fun preprocessImage(bitmap: Bitmap): ByteBuffer {
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 150,150, true)
        val byteBuffer = ByteBuffer.allocateDirect(150*150 * 3 * 4) // 4 bytes per float32
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(150*150)
        resizedBitmap.getPixels(intValues, 0, 150, 0, 0, 150, 150)
        for (pixel in intValues) {
            // Normalize the pixel values to 0-1 range
            byteBuffer.putFloat(((pixel shr 16) and 0xFF) / 255.0f) // Red
            byteBuffer.putFloat(((pixel shr 8) and 0xFF) / 255.0f)  // Green
            byteBuffer.putFloat((pixel and 0xFF) / 255.0f)           // Blue
        }
        return byteBuffer
    }

    private fun loadModelFile(context: Context,disease: String): ByteBuffer {
        lateinit var assetFileDescriptor: AssetFileDescriptor
        if(disease=="Lung cancer")
        {
            assetFileDescriptor = context.assets.openFd("Lung_model_non_quantized.tflite")
        }
        else if(disease=="Tuberculosis")
        {
            assetFileDescriptor = context.assets.openFd("Tuberculosis_model_quantized.tflite")
        }
        else if(disease=="Covid-19")
        {
            assetFileDescriptor = context.assets.openFd("Covid_model_non_quantized.tflite")
        }
        else
        {
            assetFileDescriptor = context.assets.openFd("Covid_model_non_quantized.tflite")
        }
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun runModel(byteBuffer: ByteBuffer,interpreter: Interpreter) {
        try {
            // Prepare input and output buffers
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            // Adjust output tensor shape to match the model's output
            val outputs = TensorBuffer.createFixedSize(intArrayOf(1, 2),DataType.FLOAT32)  //output shape [1, 2]

            // Run inference
            interpreter.run(inputFeature0.buffer, outputs.buffer.rewind())

            // Interpret result
            val predictions = outputs.floatArray  // This will now contain two values
            val prediction = predictions[0]
            if(disease=="Tuberculosis") {
                _result.value = if (prediction < 0.5) "$disease Not Detected" else "$disease Detected"
            }
            else{
                _result.value = if (prediction > 0.5) "$disease Not Detected" else "$disease Detected"
            }
        } catch (e: Exception) {
            Log.d("running error", e.toString())
        }
    }

    fun addReport(value: (String) -> Unit){
        ReportRepository.addReport(result.value.toString(),value = { status ->
            value(status)
        })
    }

}