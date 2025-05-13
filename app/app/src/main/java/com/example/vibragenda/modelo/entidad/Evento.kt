package com.example.vibragenda.modelo.entidad

data class Evento(
    var id: Int = 0,
    var nombre: String = "",
    var fecha: String = "",
    var lugar: String = "",
    var imagen: String = "",
    var icono: String = ""
)