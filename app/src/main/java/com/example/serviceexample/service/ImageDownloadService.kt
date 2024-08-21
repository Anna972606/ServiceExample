package com.example.serviceexample.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.serviceexample.ListenerLiveData
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ImageDownloadService : JobService() {

    private var jobParameters: JobParameters? = null

    override fun onStartJob(params: JobParameters): Boolean {
        jobParameters = params

        // Start downloading the image in a background thread
        downloadImage(jobParameters?.extras?.getString(KEY_IMAGE_URL).orEmpty())
        return true // Indicate work is ongoing
    }

    override fun onStopJob(params: JobParameters): Boolean {
        // Handle job cancellation
        return false // Don't reschedule
    }

    private fun downloadImage(imageUrl: String) {
        val client = OkHttpClient()
        val request = Request.Builder().url(imageUrl).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Failed to download image: ${e.message}")
                emitData(BitmapFactory.decodeStream(null))
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val inputStream = response.body?.byteStream()
                    emitData(BitmapFactory.decodeStream(inputStream))
                } else {
                    Log.e(TAG, "Image download failed: ${response.code}")
                    emitData(BitmapFactory.decodeStream(null))
                }
            }
        })
    }

    private fun emitData(bitmap: Bitmap?) {
        ListenerLiveData.postEvent(
            Pair(jobParameters?.extras?.getInt(KEY_IMAGE_ID) ?: 0, bitmap)
        )
        jobFinished(jobParameters, false) // Indicate work is finished
    }

    companion object {
        private const val TAG = "ImageDownloadService"
        const val KEY_IMAGE_ID = "key_image_id"
        const val KEY_IMAGE_URL = "key_image_url"
    }
}