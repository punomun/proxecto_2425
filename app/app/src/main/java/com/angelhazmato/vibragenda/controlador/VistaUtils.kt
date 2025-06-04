package com.angelhazmato.vibragenda.controlador

import android.view.View
import android.widget.ProgressBar

object VistaUtils {
    fun ocultarDatos(spinner: ProgressBar, vararg vistas: View) {
        for (vista in vistas) vista.visibility = View.GONE

        spinner.visibility = View.VISIBLE
    }

    fun mostrarDatos(spinner: ProgressBar, vararg vistas: View) {
        for (vista in vistas) vista.visibility = View.VISIBLE
        spinner.visibility = View.GONE
    }

}