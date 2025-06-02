package com.angelhazmato.vibragenda.modelo.entidad

data class Artista(
    var id: Int = 0,
    var nombre: String = "",
    var descripcion: String? = "",
    var fechaFormacion: String = "",
    var imagen: String? = "",
    var icono: String? = ""
)