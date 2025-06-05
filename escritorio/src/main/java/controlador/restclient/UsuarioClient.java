package controlador.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controlador.ConexionRest;
import java.io.IOException;
import modelo.Usuario;

public class UsuarioClient {

    private final ConexionRest conexion;
    private final ObjectMapper mapper;

    public UsuarioClient(ConexionRest conexion) {
        this.conexion = conexion;
        this.mapper = new ObjectMapper();
    }

    public Usuario get(int id) throws IOException, InterruptedException {
        String resultado = conexion.get("/api/usuarios/" + id);
        if (resultado != null) {
            return mapper.readValue(resultado, Usuario.class);
        }
        return null;
    }

    public Usuario post(Usuario usuario) throws JsonProcessingException, IOException, InterruptedException {
        String json = mapper.writeValueAsString(usuario);
        String resultado = conexion.post(json, "/api/usuarios");

        if (resultado != null) {
            return mapper.readValue(resultado, Usuario.class);
        }
        return null;
    }

    public Usuario put(int id, Usuario usuario) throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(usuario);
        String resultado = conexion.put(json, "/api/usuarios/" + id);

        if (resultado != null) {
            return mapper.readValue(resultado, Usuario.class);
        }
        return null;
    }

    public boolean delete(int id) throws IOException, InterruptedException {
        return switch (conexion.delete("/api/usuarios/" + id)) {
            case 204 ->
                true;
            default ->
                false;
        };
    }
}
