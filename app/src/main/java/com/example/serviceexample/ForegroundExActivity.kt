package com.example.serviceexample

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceexample.service.MusicForegroundService
import kotlinx.android.synthetic.main.activity_foreground_ex.buttonPause
import kotlinx.android.synthetic.main.activity_foreground_ex.buttonPlay

class ForegroundExActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreground_ex)

        buttonPlay.setOnClickListener {
            startService(Intent(this, MusicForegroundService::class.java))
        }
        buttonPause.setOnClickListener {
            stopService(Intent(this, MusicForegroundService::class.java))
        }
    }
}