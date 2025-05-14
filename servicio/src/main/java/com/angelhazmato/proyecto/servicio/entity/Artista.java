package com.angelhazmato.proyecto.servicio.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "eventos")
@Entity
@Table(name = "artista")
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_formacion")
    private LocalDate fechaFormacion;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "icono")
    private String icono;
    @ManyToMany
    @JoinTable(
            name = "artista_evento",
            joinColumns = @JoinColumn(name = "id_artista"),
            inverseJoinColumns = @JoinColumn(name = "id_evento")
    )
    @JsonIgnoreProperties("artistas")
    private Set<Evento> eventos = new HashSet<>();
}
