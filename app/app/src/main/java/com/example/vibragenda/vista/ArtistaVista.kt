package com.example.vibragenda.vista

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vibragenda.R
import com.example.vibragenda.controlador.ArtistasControlador

class ArtistaVista : AppCompatActivity() {
    lateinit var img: ImageView
    lateinit var nombre: TextView
    lateinit var desc: TextView
    lateinit var fecha: TextView
	lateinit var botonEventos: Button
    lateinit var spinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_artista_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        img = findViewById(R.id.imgVwArtista)
        nombre = findViewById(R.id.txtVwArtistaNombre)
        desc = findViewById(R.id.txtVwArtistaDesc)
        fecha = findViewById(R.id.txtVwArtistaFecha)
		botonEventos = findViewById(R.id.btnArtistaBuscarEventos)
        spinner = findViewById(R.id.progressBar)

        VistaUtils.ocultarDatos(spinner, img, nombre, desc, fecha, botonEventos)

        ArtistasControlador.cargarArtista(this)
    }
}