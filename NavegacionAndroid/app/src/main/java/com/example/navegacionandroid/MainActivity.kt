package com.example.navegacionandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.navegacionandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEnviar.setOnClickListener { enviarIntent() }
    }

    //FUNCION QUE ENVIA EL INTENT
    fun enviarIntent(){
    //PRIMERO RECUPERAR EL DATO QUE SE A ESCRITO
        var info=binding.etDato.text.toString()
        if (info.isEmpty()){
            Toast.makeText(this,"No puede estar vacio", Toast.LENGTH_SHORT).show()
        }else{
            //INTENT IMPLICITO
            var sendIntent=Intent().apply{
                action=Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,info)
                type="text/plain"
            }
            startActivity(sendIntent)
        }
    }


}