package controlador.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controlador.ConexionRest;
import java.io.IOException;
import modelo.Artista;

public class ArtistaClient {

    private final ConexionRest conexion;
    private final ObjectMapper mapper;

    public ArtistaClient(ConexionRest conexion) {
        this.conexion = conexion;
        this.mapper = new ObjectMapper();
    }

    public Artista get(int id) throws IOException, InterruptedException {
        String resultado = conexion.get("/api/artistas/" + id);
        if (resultado != null) {
            return mapper.readValue(resultado, Artista.class);
        }
        return null;
    }

    public Artista post(Artista artista) throws JsonProcessingException, IOException, InterruptedException {
        String json = mapper.writeValueAsString(artista);
        String resultado = conexion.post(json, "/api/artistas");

        if (resultado != null) {
            return mapper.readValue(resultado, Artista.class);
        }
        return null;
    }

    public Artista put(int id, Artista artista) throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(artista);
        String resultado = conexion.put(json, "/api/artistas/" + id);

        if (resultado != null) {
            return mapper.readValue(resultado, Artista.class);
        }
        return null;
    }

    public boolean delete(int id) throws IOException, InterruptedException {
        return switch (conexion.delete("/api/artistas/" + id)) {
            case 204 ->
                true;
            default ->
                false;
        };
    }

}
