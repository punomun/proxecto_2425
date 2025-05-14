package com.example.vibragenda.vista

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vibragenda.R
import com.example.vibragenda.modelo.ServicioApi
import com.example.vibragenda.vista.adaptador.ArtistaAdaptador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistasVista : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_artistas_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rvListaArtistas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch(Dispatchers.IO) {
            val listaArtistas = ServicioApi.obtenerArtistas()
            withContext(Dispatchers.Main) {
                val artistaAdaptador = ArtistaAdaptador(listaArtistas) { artista  ->
                    val intent = Intent(this@ArtistasVista, ArtistaVista::class.java)
                    intent.putExtra("ARTISTA_ID", artista.id)
                    startActivity(intent)
                }
                recyclerView.adapter = artistaAdaptador
            }
        }


    }
}