package com.angelhazmato.proyecto.servicio.entity;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="nombre")
    private String nombre;
	@Column(name="hash_contrasenha")
	private String hashContrasenha;
    @Column(name="salt")
    private String salt;
    @Column(name="perfil")
	private String perfil;
}
