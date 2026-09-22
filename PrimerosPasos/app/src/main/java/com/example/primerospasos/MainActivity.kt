package com.example.primerospasos

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.log

//LA ACTIVIDAD ES EL SISTEMA BASICO DE ANDROID
//APLICACION= CONJUNTO DE TODOS LOS SISTEMAS
class MainActivity : AppCompatActivity() {
    val CLICLO_VIDA="CICLO VDE VIDA DE LA ACTIVIDAD"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        //con esto iniciamos el contenido del activity_main
        setContentView(R.layout.activity_main)


        //SOBREESCRIBIENDO FUNCIONES
        //.- SOBREESCRIBIENDO CONSOLE
        //log = tenemos que poner un tag para decirle
        Log.d(CLICLO_VIDA,"onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(CLICLO_VIDA,"onStart")
    }


    override fun onResume() {
        super.onResume()
        Log.d(CLICLO_VIDA,"onResumen")
    }

    override fun onStop() {
        super.onStop()
        Log.d(CLICLO_VIDA,"onPause")
    }


}