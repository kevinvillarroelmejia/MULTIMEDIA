package com.example.calculadora

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Numero que se ve en la pantalla grande (empieza en 0)
    private var numeroActual = "0"
    // Primer numero de la operacion
    private var primerNumero = 0.0
    // Operador elegido (+, -, x, ÷). Vacio si no hay ninguno
    private var operador = ""
    // Si es true, el siguiente digito empieza un numero nuevo
    private var esperandoNumero = false
    // Si es true se muestra el error de division por cero
    private var hayError = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        configurarBotonesNumeros()
        configurarBotonesOperadores()
        configurarBotonesEspeciales()
        actualizarPantalla()
    }

    // Conecta los 10 botones de numeros
    private fun configurarBotonesNumeros() {
        val botonesNumeros = arrayOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9
        )
        for (boton in botonesNumeros) {
            boton.setOnClickListener {
                escribirDigito(boton.text.toString())
                actualizarPantalla()
            }
        }
    }

    // Conecta los 4 operadores
    private fun configurarBotonesOperadores() {
        binding.btnPlus.setOnClickListener {
            elegirOperador("+")
            actualizarPantalla()
        }
        binding.btnMinus.setOnClickListener {
            elegirOperador("-")
            actualizarPantalla()
        }
        binding.btnMultiply.setOnClickListener {
            elegirOperador("x")
            actualizarPantalla()
        }
        binding.btnDivide.setOnClickListener {
            elegirOperador("÷")
            actualizarPantalla()
        }
    }

    // Conecta punto, igual, C, DEL y signo
    private fun configurarBotonesEspeciales() {
        binding.btnDot.setOnClickListener {
            escribirPunto()
            actualizarPantalla()
        }
        binding.btnEquals.setOnClickListener {
            pulsarIgual()
            actualizarPantalla()
        }
        binding.btnClear.setOnClickListener {
            limpiar()
            actualizarPantalla()
        }
        binding.btnDelete.setOnClickListener {
            borrarUltimo()
            actualizarPantalla()
        }
        binding.btnSign.setOnClickListener {
            cambiarSigno()
            actualizarPantalla()
        }
    }

    // Escribe en las dos pantallas el numero actual y la operacion en curso
    private fun actualizarPantalla() {
        if (hayError) {
            binding.tvResult.text = "Error: Div por 0"
            binding.tvExpression.text = ""
        } else {
            binding.tvResult.text = numeroActual
            var expresion = ""
            if (operador != "") {
                expresion = formatearNumero(primerNumero) + operador
                if (!esperandoNumero) {
                    expresion = expresion + numeroActual
                }
            }
            binding.tvExpression.text = expresion
        }
    }

    // Anade un digito al numero actual
    private fun escribirDigito(digito: String) {
        reiniciarSiHayError()
        if (esperandoNumero || numeroActual == "0") {
            numeroActual = digito
        } else {
            numeroActual = numeroActual + digito
        }
        esperandoNumero = false
    }

    // Anade el punto decimal solo si el numero no tiene ya uno
    private fun escribirPunto() {
        reiniciarSiHayError()
        if (esperandoNumero) {
            numeroActual = "0."
            esperandoNumero = false
        } else if (!numeroActual.contains(".")) {
            numeroActual = numeroActual + "."
        }
    }

    // Guarda el operador (y resuelve la operacion anterior si estaba pendiente)
    private fun elegirOperador(nuevoOperador: String) {
        if (!hayError) {
            if (operador != "" && !esperandoNumero) {
                val resultado = calcular(primerNumero, operador, numeroActual.toDouble())
                numeroActual = formatearNumero(resultado)
            }
            if (!hayError) {
                primerNumero = numeroActual.toDouble()
                operador = nuevoOperador
                esperandoNumero = true
            }
        }
    }

    // Calcula la operacion y deja el resultado en la pantalla grande
    private fun pulsarIgual() {
        if (!hayError && operador != "" && !esperandoNumero) {
            val resultado = calcular(primerNumero, operador, numeroActual.toDouble())
            if (!hayError) {
                numeroActual = formatearNumero(resultado)
                operador = ""
                esperandoNumero = true
            }
        }
    }

    // Borra el ultimo caracter escrito
    private fun borrarUltimo() {
        if (hayError) {
            limpiar()
        } else if (!esperandoNumero) {
            numeroActual = numeroActual.substring(0, numeroActual.length - 1)
            if (numeroActual == "" || numeroActual == "-") {
                numeroActual = "0"
            }
        }
    }

    // Deja la calculadora como al principio
    private fun limpiar() {
        numeroActual = "0"
        primerNumero = 0.0
        operador = ""
        esperandoNumero = false
        hayError = false
    }

    // Cambia el signo del numero actual
    private fun cambiarSigno() {
        if (!hayError && numeroActual != "0" && (!esperandoNumero || operador == "")) {
            if (numeroActual.startsWith("-")) {
                numeroActual = numeroActual.substring(1)
            } else {
                numeroActual = "-" + numeroActual
            }
        }
    }

    // Empieza de cero si habia un error
    private fun reiniciarSiHayError() {
        if (hayError) {
            limpiar()
        }
    }

    // Hace la operacion segun el signo y marca error si se divide entre cero
    private fun calcular(valorA: Double, signo: String, valorB: Double): Double {
        var resultado = 0.0
        if (signo == "+") {
            resultado = sumar(valorA, valorB)
        } else if (signo == "-") {
            resultado = restar(valorA, valorB)
        } else if (signo == "x") {
            resultado = multiplicar(valorA, valorB)
        } else if (valorB == 0.0) {
            hayError = true
        } else {
            resultado = dividir(valorA, valorB)
        }
        return resultado
    }

    // Suma los dos valores
    private fun sumar(valorA: Double, valorB: Double): Double {
        return valorA + valorB
    }

    // Resta los dos valores
    private fun restar(valorA: Double, valorB: Double): Double {
        return valorA - valorB
    }

    // Multiplica los dos valores
    private fun multiplicar(valorA: Double, valorB: Double): Double {
        return valorA * valorB
    }

    // Divide los dos valores
    private fun dividir(valorA: Double, valorB: Double): Double {
        return valorA / valorB
    }

    // Convierte un numero en texto quitando el .0 y los errores de redondeo
    private fun formatearNumero(numero: Double): String {
        val redondeado = kotlin.math.round(numero * 1000000.0) / 1000000.0
        var texto = redondeado.toString()
        if (redondeado % 1.0 == 0.0) {
            texto = redondeado.toLong().toString()
        }
        return texto
    }
}