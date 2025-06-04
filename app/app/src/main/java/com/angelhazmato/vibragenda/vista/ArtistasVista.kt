package com.angelhazmato.vibragenda.vista

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vibragenda.R
import com.angelhazmato.vibragenda.controlador.ArtistasControlador
import com.angelhazmato.vibragenda.vista.crud.GestionarArtista
import com.google.android.material.appbar.MaterialToolbar

class ArtistasVista : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var spinner: ProgressBar
    lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_artistas_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.artistasToolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.crear_toolbar -> {
                    startActivity(Intent(this@ArtistasVista, GestionarArtista::class.java))
                    true
                }
                R.id.ajustes_toolbar -> {
					startActivity(Intent(this@ArtistasVista, AjustesVista::class.java))
                    true
                }
                else -> false
            }
        }

        recyclerView = findViewById(R.id.rvListaArtistas)
        spinner = findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        VistaUtils.ocultarDatos(spinner, recyclerView, toolbar)
        ArtistasControlador.cargarListaArtistas(this)
    }
}