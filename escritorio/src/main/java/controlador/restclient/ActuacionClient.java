package controlador.restclient;

import controlador.ConexionRest;
import java.io.IOException;

public class ActuacionClient {

    private final ConexionRest conexion;

    public ActuacionClient(ConexionRest conexion) {
        this.conexion = conexion;
    }

    public boolean get(int idArtista, int idEvento) throws IOException, InterruptedException {
        int codigoHttp = conexion.getActuacion("/api/artistas/" + idArtista + "/eventos/" + idEvento);
        return switch (codigoHttp) {
            case 200 ->
                true;
            default ->
                false;
        };
    }

    public boolean post(int idArtista, int idEvento) throws IOException, InterruptedException {
        int codigoHttp = conexion.postActuacion("/api/artistas/" + idArtista + "/eventos/" + idEvento);
        return switch (codigoHttp) {
            case 200 ->
                true;
            default ->
                false;
        };
    }

    public boolean delete(int idArtista, int idEvento) throws IOException, InterruptedException {
        return switch (conexion.delete("/api/artistas/" + idArtista + "/eventos/" + idEvento)) {
            case 204 ->
                true;
            default ->
                false;
        };
    }
}
