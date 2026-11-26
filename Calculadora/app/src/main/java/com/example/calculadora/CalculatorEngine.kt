package com.example.calculadora

class CalculatorEngine {

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

    // Devuelve el texto de la pantalla grande
    fun obtenerResultado(): String {
        var salida = numeroActual
        if (hayError) {
            salida = "Error: Dividido por 0"
        }
        return salida
    }

    // Devuelve el texto de la pantalla pequeña de la operacion que se esta haciendo
    fun obtenerExpresion(): String {
        var salida = ""
        if (!hayError && operador != "") {
            salida = formatearNumero(primerNumero) + operador
            if (!esperandoNumero) {
                salida = salida + numeroActual
            }
        }
        return salida
    }

    // Añade un digito al numero actual
    fun escribirDigito(digito: String) {
        reiniciarSiHayError()
        if (esperandoNumero || numeroActual == "0") {
            numeroActual = digito
        } else {
            numeroActual = numeroActual + digito
        }
        esperandoNumero = false
    }

    // Añade el punto decimal solo si el numero no tiene uno
    fun escribirPunto() {
        reiniciarSiHayError()
        if (esperandoNumero) {
            numeroActual = "0."
            esperandoNumero = false
        } else if (!numeroActual.contains(".")) {
            numeroActual = numeroActual + "."
        }
    }

    // Guarda el operador (y resuelve la operacion anterior si estaba pendiente)
    fun elegirOperador(nuevoOperador: String) {
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

    // Calcula la operacion y deja el resultado en la pantalla
    fun pulsarIgual() {
        if (!hayError && operador != "" && !esperandoNumero) {
            val resultado = calcular(primerNumero, operador, numeroActual.toDouble())
            if (!hayError) {
                numeroActual = formatearNumero(resultado)
                operador = ""
                esperandoNumero = true
            }
        }
    }

    // Borra el ultimo numero o signo puesto
    fun borrarUltimo() {
        if (hayError) {
            limpiar()
        } else if (!esperandoNumero) {
            numeroActual = numeroActual.substring(0, numeroActual.length - 1)
            if (numeroActual == "" || numeroActual == "-") {
                numeroActual = "0"
            }
        }
    }

    // Vuelve al inicio de la calculadora
    fun limpiar() {
        numeroActual = "0"
        primerNumero = 0.0
        operador = ""
        esperandoNumero = false
        hayError = false
    }

    // Cambia el signo del numero actual es decir el + o -
    fun cambiarSigno() {
        if (!hayError && numeroActual != "0" && (!esperandoNumero || operador == "")) {
            if (numeroActual.startsWith("-")) {
                numeroActual = numeroActual.substring(1)
            } else {
                numeroActual = "-" + numeroActual
            }
        }
    }

    // reinicia si existe un error
    private fun reiniciarSiHayError() {
        if (hayError) {
            limpiar()
        }
    }

    // Hace la operacion dependediendo  del signo y pone error a true si se divide entre cero
    private fun calcular(valorA: Double, signo: String, valorB: Double): Double {
        var resultado = 0.0
        if (signo == "+") {
            resultado = sumar(valorA, valorB)
        } else if (signo == "-") {
            resultado = restar(valorA, valorB)
        } else if (signo == "x") {
            resultado = multiplicar(valorA, valorB)
        } else if (valorB == 0.0) {
            hayError = true //pone a true el error si diivide por 0
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
    //para que sea mas manejable
    private fun formatearNumero(numero: Double): String {
        val redondeado = kotlin.math.round(numero * 1000000.0) / 1000000.0
        var texto = redondeado.toString()
        if (redondeado % 1.0 == 0.0) {
            texto = redondeado.toLong().toString()
        }
        return texto
    }
}
