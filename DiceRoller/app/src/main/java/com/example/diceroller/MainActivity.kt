package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


//HACER UN BOTON Y SACAR EL NUMERO QUE A SALIDO
class MainActivity : AppCompatActivity() {
    //FUNCION PARA BUSCAR UN ELEMENTO POR SU ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnRoll=findViewById<Button>(R.id.btnRoll)

        //dandole accion al boton
        btnRoll.setOnClickListener {
                val toast= Toast.makeText(this,"Dice rolled!", Toast.LENGTH_LONG)
                //toast.show()
            val tvdice: TextView=findViewById(R.id.tvDice)
            tvdice.text="6"
            }
        }

    }
