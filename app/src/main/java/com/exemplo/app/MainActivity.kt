package com.exemplo.app

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnStop = findViewById<Button>(R.id.btnStop)

        btnStart.setOnClickListener {
            if (temPermissaoSobreposicao()) {
                val serviceIntent = Intent(this, FloatingButtonService::class.java)
                startService(serviceIntent)
            } else {
                solicitarPermissaoSobreposicao()
            }
        }

        btnStop.setOnClickListener {
            val serviceIntent = Intent(this, FloatingButtonService::class.java)
            stopService(serviceIntent)
        }
    }

    private fun temPermissaoSobreposicao(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(this)
        } else {
            true
        }
    }

    private fun solicitarPermissaoSobreposicao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Toast.makeText(
                this,
                "Conceda a permissão 'Exibir sobre outros apps' para continuar",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        }
    }
}

