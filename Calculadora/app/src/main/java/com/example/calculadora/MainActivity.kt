package com.example.calculadora

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding




    private val motor = CalculatorEngine()

    //prepara la pantalla para cargar el xml
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // esta linea y la anterior carga el xml y lo muestra
        //esto es como por ejemplo -- cunndo sepas el tamaño de las barras ejecutar lo siguiente..
        //el view es la vista y el insets trae las medidas
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars()) //saca del insets el tamaño de las barras del SISTEMA
            view.setPadding(barras.left, barras.top, barras.right, barras.bottom) //poniendo el tamaño de las barras
            insets
        }

        configurarBotonesNumeros()
        configurarBotonesOperadores()
        botonesEspeciales()
        actualizarPantalla()
    }
    // conecta los 10 botones de numeros
    private fun configurarBotonesNumeros() {
        val botonesNumeros = arrayOf( //aarray de botones
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9
        )
        for (boton in botonesNumeros) {
            boton.setOnClickListener {
                motor.escribirDigito(boton.text.toString())
                actualizarPantalla()
            }
        }
    }
    // cuando se elige el operador
    private fun configurarBotonesOperadores() {
        binding.btnPlus.setOnClickListener {
            motor.elegirOperador("+")
            actualizarPantalla()
        }
        binding.btnMinus.setOnClickListener {
            motor.elegirOperador("-")
            actualizarPantalla()
        }
        binding.btnMultiply.setOnClickListener {
            motor.elegirOperador("x")
            actualizarPantalla()
        }
        binding.btnDivide.setOnClickListener {
            motor.elegirOperador("÷")
            actualizarPantalla()
        }
    }




    // conecta punto, igual, C, DEL y signo
    private fun botonesEspeciales() {
        binding.btnDot.setOnClickListener {
            motor.escribirPunto()
            actualizarPantalla()
        }
        binding.btnEquals.setOnClickListener {
            motor.pulsarIgual()
            actualizarPantalla()
        }
        binding.btnClear.setOnClickListener {
            motor.limpiar()
            actualizarPantalla()
        }
        binding.btnDelete.setOnClickListener {
            motor.borrarUltimo()
            actualizarPantalla()
        }
        binding.btnSign.setOnClickListener {
            motor.cambiarSigno()
            actualizarPantalla()
        }
    }




    // Escribe en las dos pantallas el numero actual y la operacion en curso
    private fun actualizarPantalla() {
        binding.tvResult.text = motor.obtenerResultado()
        binding.tvExpression.text = motor.obtenerExpresion()
    }
}
