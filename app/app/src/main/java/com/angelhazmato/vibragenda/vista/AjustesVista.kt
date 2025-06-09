package com.angelhazmato.vibragenda.vista

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.angelhazmato.vibragenda.controlador.ServicioApi
import com.example.vibragenda.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.materialswitch.MaterialSwitch

class AjustesVista : AppCompatActivity() {

    companion object {
        private const val PREF_NOMBRE = "prefs"
        private const val PREF_URL = "URL"
        private const val PREF_IDIOMA = "IDIOMA"
        private const val PREF_MODO_UI = "MODO_UI"

        fun cargarPreferencias(context: Context) {
            val preferencias = context.getSharedPreferences(PREF_NOMBRE, Context.MODE_PRIVATE)

            val url = preferencias.getString(PREF_URL, "http://192.168.0.32:8080")
            ServicioApi.url = url.toString()

            val idioma = preferencias.getString(PREF_IDIOMA, "es")
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(idioma))

            val modoUi = preferencias.getString(PREF_MODO_UI, "DIA")
            if ("DIA" == modoUi) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajustes_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val preferencias = getSharedPreferences(PREF_NOMBRE, Context.MODE_PRIVATE)

        val etUrl = findViewById<EditText>(R.id.etAjustesUrl)
        val switchIdioma = findViewById<MaterialSwitch>(R.id.switchIdioma)
        val switchModoUi = findViewById<MaterialSwitch>(R.id.switchModoUi)
        val btnGuardar = findViewById<Button>(R.id.btnAjustesGuardar)

        val url = preferencias.getString(PREF_URL, "http://192.168.0.32:8080")
        val idioma = preferencias.getString(PREF_IDIOMA, "es")
        val modoUi = preferencias.getString(PREF_MODO_UI, "DIA")

        etUrl.setText(url)

        if ("ES" == idioma) switchIdioma.isChecked = false
        else if ("GL" == idioma) switchIdioma.isChecked = true

        if ("DIA" == modoUi) switchModoUi.isChecked = false
        else switchModoUi.isChecked = true
        findViewById<MaterialToolbar>(R.id.pantalla_ajustes_toolbar).setNavigationOnClickListener { finish() }

        btnGuardar.setOnClickListener {
            val editor = preferencias.edit()

            ServicioApi.url = etUrl.text.toString()
            editor.putString(PREF_URL, etUrl.text.toString())

            val localeStr: String
            if (switchIdioma.isChecked) {
                localeStr = "gl"
                editor.putString(PREF_IDIOMA, "GL")
            } else {
                localeStr = "es"
                editor.putString(PREF_IDIOMA, "ES")
            }
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(localeStr))

            if (switchModoUi.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putString(PREF_MODO_UI, "NOCHE")
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putString(PREF_MODO_UI, "DIA")
            }

            editor.apply()
            Toast.makeText(this@AjustesVista,
                getString(R.string.cambios_guardados), Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}