package com.angelhazmato.proyecto.servicio.service;

import com.angelhazmato.proyecto.servicio.dao.EventoRepository;
import com.angelhazmato.proyecto.servicio.entity.Evento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {

    private final EventoRepository eventoRepository;

    public EventoServiceImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public List<Evento> obtenerTodos() {
        return eventoRepository.findAll();
    }

    @Override
    public Evento obtenerPorId(int id) {
        return eventoRepository.findById(id).orElse(null);
    }

    @Override
    public Evento guardar(Evento evento) {
        return eventoRepository.save(evento);
    }

    @Override
    public Evento actualizar(int id, Evento evento) {
        if (eventoRepository.existsById(id)) {
            evento.setId(id);
            return eventoRepository.save(evento);
        }
        return null;
    }

    @Override
    public boolean eliminarPorId(int id) {
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void eliminarTodos() {
        eventoRepository.deleteAll();
    }
}
