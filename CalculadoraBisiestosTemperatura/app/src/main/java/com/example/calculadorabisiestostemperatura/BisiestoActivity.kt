package com.example.calculadorabisiestostemperatura

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadorabisiestostemperatura.databinding.ActivityBisiestoBinding

class BisiestoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBisiestoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBisiestoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnComprobar.setOnClickListener {
            comprobarBisiesto()
        }
    }

    private fun comprobarBisiesto() {
        val texto = binding.etAnio.text.toString()
        val ayo = texto.toIntOrNull()

        if (ayo == null) {
            binding.tvResultado.text = "Introduce un año válido"
            return
        }

        val esBisiesto = (ayo % 4 == 0 && ayo % 100 != 0) || (ayo % 400 == 0)

        binding.tvResultado.text = if (esBisiesto) {
            "$ayo es bisiesto"
        } else {
            "$ayo no es bisiesto"
        }


    }
}