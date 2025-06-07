package com.angelhazmato.vibragenda.vista.entidad

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
import com.angelhazmato.vibragenda.controlador.EventosControlador
import com.angelhazmato.vibragenda.vista.AjustesVista
import com.angelhazmato.vibragenda.controlador.VistaUtils
import com.angelhazmato.vibragenda.vista.crud.GestionarEvento
import com.google.android.material.appbar.MaterialToolbar

class EventosVista : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var spinner: ProgressBar
    lateinit var toolbar: MaterialToolbar
	
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eventos_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.eventosToolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

		toolbar.setOnMenuItemClickListener {
			when(it.itemId) {
				R.id.crear_toolbar -> {
			        startActivity(Intent(this@EventosVista, GestionarEvento::class.java))
			        true
			    }
			    R.id.ajustes_toolbar -> {
			    	startActivity(Intent(this@EventosVista, AjustesVista::class.java))
			        true
			    }
			    else -> false
			}
		}
		
        recyclerView = findViewById(R.id.rvListaEventos)
        spinner = findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        VistaUtils.ocultarDatos(spinner, recyclerView, toolbar)
        EventosControlador.cargarListaEventos(this)
    }
}