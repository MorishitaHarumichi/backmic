package com.example.backmic

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.backmic.service.AudioRecordService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton: Button = findViewById(R.id.startButton)
        val stopButton: Button = findViewById(R.id.stopButton)

        startButton.setOnClickListener {
            startRecordingService()
        }

        stopButton.setOnClickListener {
            stopRecordingService()
        }
    }

    private fun startRecordingService() {
        val serviceIntent = Intent(this, AudioRecordService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun stopRecordingService() {
        val serviceIntent = Intent(this, AudioRecordService::class.java)
        stopService(serviceIntent)
    }
}
