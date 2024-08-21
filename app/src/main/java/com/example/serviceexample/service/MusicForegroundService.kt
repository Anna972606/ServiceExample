package com.example.serviceexample.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.serviceexample.MainActivity
import com.example.serviceexample.R

class MusicForegroundService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var notificationManager: NotificationManager

    override fun onBind(intent: Intent?): IBinder? = null
    override fun onCreate() {
        super.onCreate()

        // Create media player and load music
        mediaPlayer = MediaPlayer.create(this, R.raw.quiz_blind_test)
        mediaPlayer.isLooping = true

        // Create a NotificationChannel (required for Android O and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                MY_CHANNEL, NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager = applicationContext.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start playback (assuming you have a media source)
        mediaPlayer.start()
        updateNotification()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    private fun updateNotification() {
        val notificationBuilder = NotificationCompat.Builder(this, MY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Media Player")
            .setContentText("Playing...")
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    Intent(this, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .setOngoing(true) // Make notification ongoing
            .setAutoCancel(true) // Automatically dismiss when tapped

        // Add duration to notification content
        val duration = mediaPlayer.duration / 1000
        val minutes = duration / 60
        val seconds = duration % 60
        val durationString = String.format("%02d:%02d", minutes, seconds)
        notificationBuilder.setContentText("Playing... ($durationString)")

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        const val MY_CHANNEL_ID = "my_channel_id"
        const val MY_CHANNEL = "My Channel"
        private const val NOTIFICATION_ID = 1
    }
}