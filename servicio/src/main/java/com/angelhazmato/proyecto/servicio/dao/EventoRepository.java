package com.angelhazmato.proyecto.servicio.dao;

import com.angelhazmato.proyecto.servicio.entidad.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
}
