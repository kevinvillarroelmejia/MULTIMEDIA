package com.example.calculadorabisiestostemperatura

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadorabisiestostemperatura.databinding.ActivityTemperaturaBinding

class TemperatureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTemperaturaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTemperaturaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConvertir.setOnClickListener {
            convertir()
        }
    }

    private fun convertir() {
        val texto = binding.etTemperatura.text.toString()
        val valor = texto.toDoubleOrNull()

        if (valor == null) {
            binding.tvResultado.text = "Introduce una temperatura válida"
            return
        }

        var enCelsius = valor
        if (binding.rgOrigen.checkedRadioButtonId == R.id.rb_origen_fahrenheit) {
            enCelsius = (valor - 32) * 5 / 9
        } else if (binding.rgOrigen.checkedRadioButtonId == R.id.rb_origen_kelvin) {
            enCelsius = valor - 273.15
        }

        var resultado = enCelsius
        if (binding.rgDestino.checkedRadioButtonId == R.id.rb_destino_fahrenheit) {
            resultado = enCelsius * 9 / 5 + 32
        } else if (binding.rgDestino.checkedRadioButtonId == R.id.rb_destino_kelvin) {
            resultado = enCelsius + 273.15
        }

        //esto seria como el printF de java
        binding.tvResultado.text = "Resultado: %.2f".format(resultado)
    }
}