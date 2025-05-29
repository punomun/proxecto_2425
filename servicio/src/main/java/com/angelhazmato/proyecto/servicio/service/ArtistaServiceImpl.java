package com.angelhazmato.proyecto.servicio.service;

import com.angelhazmato.proyecto.servicio.dao.ArtistaRepository;
import com.angelhazmato.proyecto.servicio.dao.EventoRepository;
import com.angelhazmato.proyecto.servicio.entity.Artista;
import com.angelhazmato.proyecto.servicio.entity.Evento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaServiceImpl implements ArtistaService {

    private final ArtistaRepository artistaRepository;
	private final EventoRepository eventoRepository;

    public ArtistaServiceImpl(ArtistaRepository artistaRepository, EventoRepository eventoRepository) {
        this.artistaRepository = artistaRepository;
		this.eventoRepository = eventoRepository;
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

	@Override
	public boolean existeActuacion(int idArtista, int idEvento) {
		Optional<Artista> artistaOptional = artistaRepository.findById(idArtista);
		Optional<Evento> eventoOptional = eventoRepository.findById(idEvento);

		if (artistaOptional.isPresent() && eventoOptional.isPresent()) return artistaOptional.get().getEventos().contains(eventoOptional.get());
		return false;
	}
	
	@Override
	@Transactional
	public boolean inscribirArtistaEnEvento(int idArtista, int idEvento) {
		Optional<Artista> artistaOptional = artistaRepository.findById(idArtista);
		Optional<Evento> eventoOptional = eventoRepository.findById(idEvento);
		
		if (artistaOptional.isPresent() && eventoOptional.isPresent()) {
			Artista artista = artistaOptional.get();
			Evento evento = eventoOptional.get();
			if (artista.getEventos().contains(evento)) return false;
			artista.getEventos().add(evento);
			artistaRepository.save(artista);
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional
	public boolean borrarArtistaDeEvento(int idArtista, int idEvento) {
		Optional<Artista> artistaOptional = artistaRepository.findById(idArtista);
		Optional<Evento> eventoOptional = eventoRepository.findById(idEvento);
		
		if (artistaOptional.isPresent() && eventoOptional.isPresent()) {
			Artista artista = artistaOptional.get();
			Evento evento = eventoOptional.get();
			if (!artista.getEventos().contains(evento)) return false;
			artista.getEventos().remove(evento);
			artistaRepository.save(artista);
			return true;
		}
		return false;
	}   
}
