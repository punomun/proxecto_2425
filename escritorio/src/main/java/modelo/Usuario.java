package modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {
    private int id;
    private String nombre;
    private String hashContrasenha;
    private String salt;
    private String perfil;

    public Usuario(String nombre, String hashContrasenha, String perfil) {
        this.nombre = nombre;
        this.hashContrasenha = hashContrasenha;
        this.perfil = perfil;
    }
}
