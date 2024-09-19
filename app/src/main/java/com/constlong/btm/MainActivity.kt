package com.constlong.btm


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket

import android.util.Log
import androidx.activity.enableEdgeToEdge



class MainActivity : AppCompatActivity() {
    private lateinit var tvData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvData = findViewById(R.id.tvData);

        CoroutineScope(Dispatchers.IO).launch {
            startServer()
        }
    }
    private fun startServer() {
        try {
            val serverSocket = ServerSocket(44370)
            val clientSocket = serverSocket.accept()
            val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))

            while (true) {
                val message = reader.readLine()
                Log.d("btm","message read:$message")
                if (message != null) {
                    runOnUiThread {
                        tvData.text = message
                    }
                }            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}