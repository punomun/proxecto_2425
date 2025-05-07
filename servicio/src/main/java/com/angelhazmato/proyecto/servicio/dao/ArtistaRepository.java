package com.angelhazmato.proyecto.servicio.dao;

import com.angelhazmato.proyecto.servicio.entity.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository<Artista, Integer> {
}
