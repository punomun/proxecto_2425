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
@EqualsAndHashCode(exclude = "artistas")
@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "lugar")
    private String lugar;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "icono")
    private String icono;
    @ManyToMany(mappedBy = "eventos")
    @JsonIgnoreProperties("eventos")
    private Set<Artista> artistas = new HashSet<>();
}
