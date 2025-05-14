package com.angelhazmato.proyecto.servicio.dao;

import com.angelhazmato.proyecto.servicio.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByArtistasId(int artistasId);
}
