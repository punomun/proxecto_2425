package controlador.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import controlador.ConexionRest;
import java.io.IOException;
import modelo.Evento;

public class EventoClient {

    private final ConexionRest conexion;
    private final ObjectMapper mapper;

    public EventoClient(ConexionRest conexion) {
        this.conexion = conexion;
        this.mapper = new ObjectMapper();
    }

    public Evento get(int id) throws IOException, InterruptedException {
        String resultado = conexion.get("/api/eventos/" + id);
        if (resultado != null) {
            return mapper.readValue(resultado, Evento.class);
        }

        return null;
    }

    public Evento post(Evento evento) throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(evento);
        String resultado = conexion.post(json, "/api/eventos");

        if (resultado != null) {
            return mapper.readValue(resultado, Evento.class);
        }
        return null;
    }

    public Evento put(int id, Evento evento) throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(evento);
        String resultado = conexion.put(json, "/api/eventos/" + id);

        if (resultado != null) {
            return mapper.readValue(resultado, Evento.class);
        }
        return null;
    }

    public boolean delete(int id) throws IOException, InterruptedException {
        return switch (conexion.delete("/api/eventos/" + id)) {
            case 204 ->
                true;
            default ->
                false;
        };
    }
}
