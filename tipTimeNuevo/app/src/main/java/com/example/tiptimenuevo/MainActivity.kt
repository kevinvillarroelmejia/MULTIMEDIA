package com.example.tiptimenuevo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiptimenuevo.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
// Mostramos el diseño de activity_main.xml
        setContentView(binding.root)
// Vinculamos el boton con la funcion calculateTip
        binding.calculateButton.setOnClickListener {
            calculateTip()
        }
    }

    // METODO QUE CALCULA LA PROPINA
    private fun calculateTip() {
// RECOGEMOS EL VALOR DEL TEXTO
        val stringInTextField = binding.costOfService.text.toString()
// CONVERTIMOS EL VALOR A UN DOUBLE , PODEMOS HACERLO DIRECTAMENTE EN LA LINEA ANTERIOR
        val cost = stringInTextField.toDoubleOrNull()
// SI EL VALOR ES NULL , SALIMOS DE LA FUNCION
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }
// SELECCIONAMOS EL PORCENTAJE DE LA PROPINA
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
// REALIZAMOS EL CALCULO DE LA PROPINA
        var tip = tipPercentage * cost
// REDONDEAMOS LA PROPINA
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
// FORMATO DE MONEDA
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
// MOSTRAMOS EL RESULTADO EN LA CAJA DE TEXTO
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}