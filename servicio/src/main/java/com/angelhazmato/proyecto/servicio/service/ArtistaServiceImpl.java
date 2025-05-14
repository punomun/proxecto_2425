package com.angelhazmato.proyecto.servicio.service;

import com.angelhazmato.proyecto.servicio.dao.ArtistaRepository;
import com.angelhazmato.proyecto.servicio.entity.Artista;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistaServiceImpl implements ArtistaService {

    private final ArtistaRepository artistaRepository;

    public ArtistaServiceImpl(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    @Override
    public List<Artista> obtenerTodos() {
        return artistaRepository.findAll();
    }

    @Override
    public Artista obtenerPorId(int id) {
        return artistaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Artista> obtenerPorIdEvento(int idEvento) {
        return artistaRepository.findByEventosId(idEvento);
    }

    @Override
    public Artista guardar(Artista artista) {
        return artistaRepository.save(artista);
    }

    @Override
    public Artista actualizar(int id, Artista artista) {
        if (artistaRepository.existsById(id)) {
            artista.setId(id);
            return artistaRepository.save(artista);
        }
        return null;
    }

    @Override
    public boolean eliminarPorId(int id) {
        if (artistaRepository.existsById(id)) {
            artistaRepository.deleteById(id);
            return true;
        }
        return false;
    }
	
	@Override
    public void eliminarTodos() {
        artistaRepository.deleteAll();
    }
}
