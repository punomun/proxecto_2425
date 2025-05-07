package com.angelhazmato.proyecto.servicio.controller;

import com.angelhazmato.proyecto.servicio.entity.Evento;
import com.angelhazmato.proyecto.servicio.service.EventoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoRestController {

    private final EventoService eventoService;

    public EventoRestController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<Evento>> getEventos() {
        return ResponseEntity.ok(eventoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> getEvento(@PathVariable int id) {
        Evento evento = eventoService.obtenerPorId(id);
        if (evento == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(evento);
    }

    @PostMapping
    public ResponseEntity<Evento> postEvento(@RequestBody Evento evento) {
        Evento nuevoEvento = eventoService.guardar(evento);
        URI locationHeader = URI.create("/api/eventos/" + nuevoEvento.getId());
        return ResponseEntity.created(locationHeader).body(nuevoEvento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> putEvento(@PathVariable int id, @RequestBody Evento evento) {
        Evento eventoActualizado = eventoService.actualizar(id, evento);
        if (eventoActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(eventoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable int id) {
        if (eventoService.eliminarPorId(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEventos() {
        eventoService.eliminarTodos();
        return ResponseEntity.noContent().build();
    }
}
