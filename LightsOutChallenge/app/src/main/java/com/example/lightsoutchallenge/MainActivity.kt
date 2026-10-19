package com.example.lightsoutchallenge

import android.content.Context
import android.content.res.ColorStateList
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lightsoutchallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    /*
    IMPORTANTE PONER ESTO EN build.granle.kts para que funcione el binding
    *     buildFeatures {
        viewBinding = true
    }*/
    private lateinit var binding: ActivityMainBinding

    //la matriz logica que representa el tablero
    private val estado = Array(3) { BooleanArray(3) }

    // Matriz de botones que representa el tablero de manera visual
    private lateinit var botones: Array<Array<Button>>


    private lateinit var soundPool: SoundPool
    private var sonidoClic = 0
    private var sonidoVictoria = 0

    private var contMovimientos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //inicializamos la matriz enlazando con el binding
        botones = arrayOf(
            arrayOf(binding.btn0, binding.btn1, binding.btn2),
            arrayOf(binding.btn3, binding.btn4, binding.btn5),
            arrayOf(binding.btn6, binding.btn7, binding.btn8)
        )

        //
        for (f in 0..2) {
            for (c in 0..2) {
                botones[f][c].setOnClickListener {
                    cambiaValorVecinos(f, c)
                    if (existeVictoria()) {
                        soundPool.play(sonidoVictoria, 1f, 1f, 1, 0, 1f)
                        //mensaje cuando ganamos
                        Toast.makeText(this, "¡Has ganado!", Toast.LENGTH_LONG).show()
                        soundPool.play(sonidoVictoria,1f,1f,1,0,1f)
                    }
                }
            }
        }

        //reiniciamos el juego
        binding.btnReiniciar.setOnClickListener {
            reiniciar()
        }

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        sonidoClic = soundPool.load(this, R.raw.sonido_clic, 1)
        sonidoVictoria = soundPool.load(this, R.raw.sonido_victoria, 1)

    }

    private fun vibrar() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(50)
        }
    }

    private fun existeVictoria(): Boolean{
        var salida=true
        for(f in 0..2){
            for(c in 0..2){
                if(!estado[f][c]){
                    salida=false
                }
            }
        }
        return salida
    }
    private fun reiniciar(){
        for (f in 0..2) {
            for (c in 0..2) {
                estado[f][c]=false
                pintarBoton(botones[f][c], estado[f][c])
            }
        }
        contMovimientos=0
        binding.tvContador.text = "Movimientos: ${contMovimientos}"
    }

    //valida si la posicion EXISTE
    private fun posicionValida(f: Int,c:Int): Boolean{
        //f y c tienen que estar entre 0 y 2
        if (f in 0..2 && c in 0..2) {
            // posición válida
            return true
        }else{
            return false
        }
    }


    private fun pintarBoton(boton: Button, encendido: Boolean) {
        val color = if (encendido) { //SI ESTA ENCENDIDO RECOGE EL COLOR DEL XML
            ContextCompat.getColor(this, R.color.colorPrimario)
        } else { //SI ESTA APAGADO RECOGE EL COLOR DEL XML
            ContextCompat.getColor(this, R.color.colorSecundario)
        }
        //TIÑE EL BOTON CON EL COLOR
        boton.backgroundTintList = ColorStateList.valueOf(color)
    }


    //FUNCION QUE CMABIA EL ESTADO true/false DE UNA CELDA
    private fun cambiarValorCelda(f: Int, c:Int){
        if(posicionValida(f,c)){
            //cambiando el estado de la celda
            estado[f][c]=!estado[f][c]
            pintarBoton(botones[f][c], estado[f][c])
        }
    }


    //FUNCIONA QUE SE LLAMA CUANDO SE HACE CLICK EN UNA CELDA -
    //cambia esa celda y sus 4 vecinos
    private fun cambiaValorVecinos(f: Int, c:Int){
        vibrar()
        soundPool.play(sonidoClic, 1f, 1f, 1, 0, 1f)
        cambiarValorCelda(f, c)  //CAMBIEN VALOR DE la propia celda pulsada
        cambiarValorCelda(f - 1, c)   // CAMBIEN VALOR arriba
        cambiarValorCelda(f + 1, c)   // CAMBIEN VALOR abajo
        cambiarValorCelda(f, c - 1)   // CAMBIEN VALOR izquierda
        cambiarValorCelda(f, c + 1)   // CAMBIEN VALOR derecha
        binding.tvContador.text = "Movimientos: ${++contMovimientos}"
    }


}