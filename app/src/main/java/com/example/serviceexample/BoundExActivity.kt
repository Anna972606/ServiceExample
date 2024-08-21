package com.example.serviceexample

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceexample.service.MusicBoundService
import kotlinx.android.synthetic.main.activity_bound_ex.buttonPause
import kotlinx.android.synthetic.main.activity_bound_ex.buttonPlay

class BoundExActivity : AppCompatActivity() {

    private var musicBoundService: MusicBoundService? =null
    private val connection = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicBoundService.LocalBinder
            musicBoundService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // Handle service disconnection
            musicBoundService = null
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bound_ex)

        // Bind to the music service
        val intent = Intent(this, MusicBoundService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)

        buttonPlay.setOnClickListener {
            musicBoundService?.playMusic()
        }
        buttonPause.setOnClickListener {
            musicBoundService?.pauseMusic()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }
}