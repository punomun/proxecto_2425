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
import com.example.vibragenda.vista.adaptador.EventoAdaptador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventosVista : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eventos_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rvListaEventos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch(Dispatchers.IO) {
            val listaEventos = ServicioApi.obtenerEventos()
            withContext(Dispatchers.Main) {
                val eventoAdaptador = EventoAdaptador(listaEventos) { evento  ->
                    val intent = Intent(this@EventosVista, EventoVista::class.java)
                    intent.putExtra("EVENTO_ID", evento.id)
                    startActivity(intent)
                }
                recyclerView.adapter = eventoAdaptador
            }
        }
    }
}