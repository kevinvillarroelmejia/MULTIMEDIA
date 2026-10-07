package com.example.navegacionandroid

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.navegacionandroid.databinding.ActivityMainBinding

class SecondActivity2 : AppCompatActivity() {

    companion object {
        const val DATO_NOMBRE = "nombre"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second2)
        var nombre = intent.getStringExtra(DATO_NOMBRE)
        findViewById<TextView>(R.id.tvSaludo).text = "Hola $nombre"
    }
}


