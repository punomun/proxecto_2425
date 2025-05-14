package com.angelhazmato.proyecto.servicio.service;

import com.angelhazmato.proyecto.servicio.entity.Evento;

import java.util.List;

public interface EventoService {
    List<Evento> obtenerTodos();
    Evento obtenerPorId(int id);
    List<Evento> obtenerPorIdArtista(int idArtista);
    Evento guardar(Evento evento);
    Evento actualizar(int id, Evento evento);
    boolean eliminarPorId(int id);
    void eliminarTodos();
}
