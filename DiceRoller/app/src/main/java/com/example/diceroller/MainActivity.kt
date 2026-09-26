package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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
            rollDice()
        }
        rollDice()
    }
    private fun rollDice() {
        //lanzamos dado del 1 al 6
        val dice = Dice(6)
        //tiramos cada dado por separado
        val diceRoll1 = dice.roll()
        val diceRoll2 = dice.roll()

        //====inicializamos las imagenes a dos variables//====
        val diceImage1: ImageView = findViewById(R.id.imageView)
        val diceImage2: ImageView = findViewById(R.id.imageView2)

        diceImage1.setImageResource(getDiceImage(diceRoll1))
        diceImage1.contentDescription = diceRoll1.toString()

        diceImage2.setImageResource(getDiceImage(diceRoll2))
        diceImage2.contentDescription = diceRoll2.toString()
    }

    //recibe el numero y devuelve la imagen que le corresponde
    //====Esta funcion recibe un int y devuelve un int===
    private fun getDiceImage(diceRoll: Int): Int {
        return when (diceRoll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

    }
}
