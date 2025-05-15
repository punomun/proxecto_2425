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
import com.example.vibragenda.controlador.EventosControlador

class EventosVista : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var spinner: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eventos_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.rvListaEventos)
        spinner = findViewById(R.id.progressBar)

        VistaUtils.ocultarDatos(spinner, recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        EventosControlador.cargarListaEventos(this)
    }
}