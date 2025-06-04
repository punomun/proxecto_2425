package com.angelhazmato.vibragenda.vista

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vibragenda.R

class ExcepcionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_excepcion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AlertDialog.Builder(this@ExcepcionActivity).setTitle("Error de conexión")
            .setMessage("Ha ocurrido un error en la conexión con el servicio.\n¿Deseas cambiar la URL?")
            .setPositiveButton("Ir a ajustes") { _, _ ->
                startActivity(
                    Intent(
                        this@ExcepcionActivity, AjustesVista::class.java
                    )
                )
                finish()
            }.setNegativeButton("Salir de la aplicación") { _, _ ->
                finishAffinity()
            }.setCancelable(false).create().show()
    }
}