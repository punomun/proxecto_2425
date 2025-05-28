package com.angelhazmato.vibragenda.vista

import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vibragenda.R
import com.angelhazmato.vibragenda.controlador.EventosControlador

class EventoVista : AppCompatActivity() {
    lateinit var img: ImageView
    lateinit var nombre: TextView
    lateinit var fecha: TextView
    lateinit var lugar: TextView
    lateinit var artistasConfirmados: TextView
    lateinit var spinner: ProgressBar
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_evento_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        img = findViewById(R.id.imgVwEvento)
        nombre = findViewById(R.id.txtVwEventoNombre)
        fecha = findViewById(R.id.txtVwEventoFecha)
        lugar = findViewById(R.id.txtVwEventoLugar)
        artistasConfirmados = findViewById(R.id.txtVwEventoArtistas)
        spinner = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.rvEventoArtistas)

        VistaUtils.ocultarDatos(
            spinner,
            img,
            nombre,
            fecha,
            lugar,
            artistasConfirmados,
            recyclerView
        )

        recyclerView.layoutManager = LinearLayoutManager(this)

        EventosControlador.cargarEvento(this)
    }
}