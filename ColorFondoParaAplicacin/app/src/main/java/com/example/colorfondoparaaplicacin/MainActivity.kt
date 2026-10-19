package com.example.colorpicker

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.colorfondoparaaplicacin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val colorPickerLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        object : ActivityResultCallback<androidx.activity.result.ActivityResult> {
            override fun onActivityResult(result: androidx.activity.result.ActivityResult) {
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data
                    if (data != null) {
                        val colorHex = data.getStringExtra("COLOR_HEX")
                        val colorName = data.getStringExtra("COLOR_NAME")
                        if (colorHex != null) {
                            binding.rootLayout.setBackgroundColor(Color.parseColor(colorHex))
                        }
                        binding.tvColorActual.text = "Color actual: $colorName"
                    }
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnElegirColor.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val intent = Intent(this@MainActivity, ColorPickerActivity::class.java)
                colorPickerLauncher.launch(intent)
            }
        })
    }
}