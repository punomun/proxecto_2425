package com.angelhazmato.proyecto.servicio.entidad;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name="evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="nombre")
    private String nombre;
	@Column(name="fecha")
    private LocalDate fecha;
	@Column(name="lugar")
	private String lugar;
	@Column(name="imagen")
	private String imagen;
	@Column(name="icono")
	private String icono;

    public Evento(String nombre, LocalDate fecha, String lugar, String imagen, String icono) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
        this.imagen = imagen;
        this.icono = icono;
    }
}
