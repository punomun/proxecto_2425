package com.angelhazmato.vibragenda.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.angelhazmato.vibragenda.controlador.ControladorExcepciones
import com.example.vibragenda.R
import com.angelhazmato.vibragenda.vista.entidad.ArtistasVista
import com.angelhazmato.vibragenda.vista.entidad.EventosVista

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Thread.setDefaultUncaughtExceptionHandler(ControladorExcepciones(applicationContext))
        AjustesVista.cargarPreferencias(this)

        val btnEventos = findViewById<Button>(R.id.btnVerEventos)
        val btnArtistas = findViewById<Button>(R.id.btnVerArtistas)

        btnEventos.setOnClickListener {
            val intent = Intent(this@MainActivity, EventosVista::class.java)
            startActivity(intent)
        }

        btnArtistas.setOnClickListener {
            val intent = Intent(this@MainActivity, ArtistasVista::class.java)
            startActivity(intent)
        }
    }
}