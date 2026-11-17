package com.example.calculadora

class CalculatorEngine {

    private var numeroActual = ""
    private var primerNumero = 0.0
    // El operador elegido (+, -, x, ÷). Vacio si no hay ninguno
    private var operador = ""
    // El ultimo resultado que se muestra cuando no se esta escribiendo nada
    private var textoResultado = "0"

    // La operacion terminada, por ejemplo 12.5+4=
    private var textoExpresion = ""

    // Si es true se muestra el error de division por cero
    private var hayError = false

    // Si es true acabamos de pulsar el igual
    private var acabaDeCalcular = false

    // Devuelve el texto que va en la pantalla grande
    fun obtenerResultado(): String {
        var salida = textoResultado
        if (hayError) {
            salida = "Error: Div por 0"
        } else if (numeroActual != "") {
            salida = numeroActual
        }
        return salida
    }

    // Devuelve el texto que va en la pantalla pequena
    fun obtenerExpresion(): String {
        var salida = ""
        if (hayError) {
            salida = ""
        } else if (acabaDeCalcular) {
            salida = textoExpresion
        } else if (operador != "") {
            salida = formatearNumero(primerNumero) + operador + numeroActual
        }
        return salida
    }

    // Anade un digito al numero que se esta escribiendo
    fun escribirDigito(digito: String) {
        reiniciarSiHaceFalta()
        if (numeroActual == "0") {
            numeroActual = digito
        } else {
            numeroActual = numeroActual + digito
        }
    }

    // Anade el punto decimal solo si el numero todavia no tiene uno
    fun escribirPunto() {
        reiniciarSiHaceFalta()
        if (!numeroActual.contains(".")) {
            if (numeroActual == "") {
                numeroActual = "0."
            } else {
                numeroActual = numeroActual + "."
            }
        }
    }

    // Guarda el operador y, si ya habia una operacion pendiente, la resuelve antes
    fun elegirOperador(nuevoOperador: String) {
        if (hayError) return
        val segundoNumero = numeroActual.toDoubleOrNull()
        if (segundoNumero != null) {
            if (operador != "") {
                primerNumero = calcular(primerNumero, operador, segundoNumero)
                if (hayError) return
            } else {
                primerNumero = segundoNumero
            }
            numeroActual = ""
            textoResultado = formatearNumero(primerNumero)
        }
        operador = nuevoOperador
        acabaDeCalcular = false
    }

    // Calcula la operacion pendiente y muestra el resultado
    fun pulsarIgual() {
        val segundoNumero = numeroActual.toDoubleOrNull()
        if (hayError || operador == "" || segundoNumero == null) return
        val resultado = calcular(primerNumero, operador, segundoNumero)
        if (hayError) return
        textoExpresion = formatearNumero(primerNumero) + operador + numeroActual + "="
        textoResultado = formatearNumero(resultado)
        primerNumero = resultado
        operador = ""
        numeroActual = ""
        acabaDeCalcular = true
    }

    // Borra el ultimo caracter que se ha escrito
    fun borrarUltimo() {
        if (hayError) {
            limpiar()
        } else if (numeroActual != "") {
            numeroActual = numeroActual.substring(0, numeroActual.length - 1)
            if (numeroActual == "-") {
                numeroActual = ""
            }
        }
    }

    // Deja la calculadora como al principio
    fun limpiar() {
        numeroActual = ""
        primerNumero = 0.0
        operador = ""
        textoResultado = "0"
        textoExpresion = ""
        hayError = false
        acabaDeCalcular = false
    }

    // Cambia el signo del numero que se esta escribiendo o del ultimo resultado
    fun cambiarSigno() {
        if (hayError) return
        if (numeroActual != "") {
            if (numeroActual.startsWith("-")) {
                numeroActual = numeroActual.substring(1)
            } else {
                numeroActual = "-" + numeroActual
            }
        } else if (acabaDeCalcular) {
            primerNumero = -primerNumero
            textoResultado = formatearNumero(primerNumero)
        }
    }

    // Convierte el numero que se esta escribiendo en su porcentaje
    fun calcularPorcentaje() {
        val numero = numeroActual.toDoubleOrNull()
        if (numero != null) {
            numeroActual = formatearNumero(numero / 100)
        }
    }

    // Empieza de cero si habia un error o acabamos de calcular
    private fun reiniciarSiHaceFalta() {
        if (hayError || acabaDeCalcular) {
            limpiar()
        }
    }

    // Marca el error de division por cero y borra la operacion
    private fun marcarError() {
        hayError = true
        numeroActual = ""
        operador = ""
        primerNumero = 0.0
        acabaDeCalcular = false
    }

    // Elige la operacion segun el signo y devuelve el resultado
    private fun calcular(valorA: Double, signo: String, valorB: Double): Double {
        var resultado = 0.0
        when (signo) {
            "+" -> resultado = sumar(valorA, valorB)
            "-" -> resultado = restar(valorA, valorB)
            "x" -> resultado = multiplicar(valorA, valorB)
            "÷" -> {
                if (valorB == 0.0) {
                    marcarError()
                } else {
                    resultado = dividir(valorA, valorB)
                }
            }
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

    // Divide los dos valores (el cero se comprueba antes en calcular)
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