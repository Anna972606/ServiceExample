package com.example.serviceexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.buttonBoundService
import kotlinx.android.synthetic.main.activity_main.buttonForeground
import kotlinx.android.synthetic.main.activity_main.buttonJobScheduler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonBoundService.setOnClickListener {
           startActivity(Intent(this, BoundExActivity::class.java))
        }
        buttonForeground.setOnClickListener {
            startActivity(Intent(this, ForegroundExActivity::class.java))
        }
        buttonJobScheduler.setOnClickListener {
            startActivity(Intent(this, JobSchedulerExActivity::class.java))
        }
    }
}