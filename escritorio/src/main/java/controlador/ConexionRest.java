package controlador;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class ConexionRest {

    public static String urlRest = "";
    private HttpClient client;

    public ConexionRest() {
        client = HttpClient.newHttpClient();
    }

    public String get(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlRest + endpoint))
                .GET()
                .build();

        HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString());

        return switch (respuesta.statusCode()) {
            case 200 ->
                respuesta.body();
            default ->
                null;
        };
    }

    public String post(String json, String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlRest + endpoint))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString());

        return switch (respuesta.statusCode()) {
            case 201 ->
                respuesta.body();
            default ->
                null;
        };
    }

    public String put(String json, String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlRest + endpoint))
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString());

        return switch (respuesta.statusCode()) {
            case 200 ->
                respuesta.body();
            default ->
                null;
        };
    }

    public int delete(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlRest + endpoint))
                .DELETE()
                .build();
        HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString());
        System.out.println(respuesta.statusCode());
        return respuesta.statusCode();
    }

    public int getActuacion(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlRest + endpoint))
                .GET()
                .build();

        HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString());

        return respuesta.statusCode();
    }

    public int postActuacion(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlRest + endpoint))
                .POST(BodyPublishers.noBody())
                .build();

        HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString());

        return respuesta.statusCode();
    }
}
