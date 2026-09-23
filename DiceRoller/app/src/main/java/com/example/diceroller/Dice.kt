package com.example.diceroller

//Definiendo el construtor del dado
//numero de caras
class Dice(val numSides: Int) {
    //FUNCION que no recibe nada pero devuelve un entero
    //numero random entre el 1 y numero de caras
    fun roll() : Int{
        return (1 .. numSides).random()
    }
}