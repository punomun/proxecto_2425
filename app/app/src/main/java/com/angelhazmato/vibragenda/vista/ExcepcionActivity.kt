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
        AlertDialog.Builder(this@ExcepcionActivity).setTitle(getString(R.string.error_conexion_titulo))
            .setMessage(getString(R.string.error_conexion))
            .setPositiveButton(getString(R.string.error_conexion_aceptar)) { _, _ ->
                startActivity(
                    Intent(
                        this@ExcepcionActivity, AjustesVista::class.java
                    )
                )
                finish()
            }.setNegativeButton(getString(R.string.error_conexion_denegar)) { _, _ ->
                finishAffinity()
            }.setCancelable(false).create().show()
    }
}