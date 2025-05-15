package com.example.vibragenda.vista

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vibragenda.R
import com.example.vibragenda.controlador.ArtistasControlador

class ArtistasVista : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var spinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_artistas_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.rvListaArtistas)
        spinner = findViewById(R.id.progressBar)

        VistaUtils.ocultarDatos(spinner, recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        ArtistasControlador.cargarListaArtistas(this)
    }
}