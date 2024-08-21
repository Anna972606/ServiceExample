package com.example.serviceexample.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.serviceexample.R
import java.io.IOException

class MusicBoundService : Service() {
    private var binder = LocalBinder()
    private var mediaPlayer: MediaPlayer? = null

    inner class LocalBinder : Binder() {
        fun getService() = this@MusicBoundService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun playMusic() {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.quiz_blind_test)
            }
            mediaPlayer?.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun pauseMusic() {
        if (mediaPlayer != null && mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}