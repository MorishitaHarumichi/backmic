package com.example.backmic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.backmic.service.AudioRecordService

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.padding(16.dp)) {
                    Column {
                        StartStopButtons()
                    }
                }
            }
        }
    }
}

@Composable
fun StartStopButtons() {
    val context = LocalContext.current

    Button(onClick = {
        startRecordingService(context)
    }) {
        Text("Start Recording")
    }

    Button(onClick = {
        stopRecordingService(context)
    }) {
        Text("Stop Recording")
    }
}

fun startRecordingService(context: Context) {
    val serviceIntent = Intent(context, AudioRecordService::class.java)
    ContextCompat.startForegroundService(context, serviceIntent)
}

fun stopRecordingService(context: Context) {
    val serviceIntent = Intent(context, AudioRecordService::class.java)
    context.stopService(serviceIntent)
}
