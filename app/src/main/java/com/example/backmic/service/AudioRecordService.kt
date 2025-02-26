package com.example.backmic.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.app.Service
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.io.File

class AudioRecordService : Service() {

    private lateinit var mediaRecorder: MediaRecorder
    private var isRecording = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startRecording()
        return START_STICKY
    }

    private fun startRecording() {
        try {
            if (!isRecording) {
                val outputDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC)
                val outputFilePath = File(outputDir, "recorded_audio.mp4").absolutePath

                mediaRecorder = MediaRecorder().apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                    setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                    setOutputFile(outputFilePath)
                    prepare()
                    start()
                }
                isRecording = true
                Log.d("AudioRecordService", "Recording started")
            }
        } catch (e: Exception) {
            Log.e("AudioRecordService", "Error starting recording", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRecording) {
            try {
                mediaRecorder.stop()
                mediaRecorder.release()
                isRecording = false
                Log.d("AudioRecordService", "Recording stopped")
            } catch (e: Exception) {
                Log.e("AudioRecordService", "Error stopping recording", e)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "audio_recording_channel",
                "Audio Recording",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, "audio_recording_channel")
            .setContentTitle("Recording Audio")
            .setContentText("Audio recording is in progress")
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
