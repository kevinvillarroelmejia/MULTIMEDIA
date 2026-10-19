package com.example.colorpicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.colorfondoparaaplicacin.databinding.ActivityColorPickerBinding

class ColorPickerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityColorPickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityColorPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRojo.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                devolverColor("#F44336", "Rojo")
            }
        })

        binding.btnVerde.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                devolverColor("#4CAF50", "Verde")
            }
        })

        binding.btnAzul.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                devolverColor("#2196F3", "Azul")
            }
        })
    }

    private fun devolverColor(hex: String, nombre: String) {
        val intent = Intent()
        intent.putExtra("COLOR_HEX", hex)
        intent.putExtra("COLOR_NAME", nombre)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}