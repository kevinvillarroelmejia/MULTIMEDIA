package com.example.conociendorecicleview

import android.os.Binder
import android.os.Bundle
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.conociendorecicleview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val dataset = arrayOf("January", "February", "March", "April", "May",
            "June", "July" , "August", " September", "October", "November", "December")

        val adapter = Adapter(dataset)

        //recogemos la vista de la lista
        val rvMonths= binding.rvMonths

        rvMonths.layoutManager= LinearLayoutManager(this)
        rvMonths.adapter=adapter


        }
    }
