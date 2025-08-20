package com.example.LiteraturaChallenge;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class obtenerDatos {

    private static final String URL_API = "https://gutendex.com/books/";

    public String obtenerDatos(String s) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_API + s))
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body();
    }

    public String buscarLibroPorTitulo(String titulo){
        return obtenerDatos("?search=" + titulo.replace(" ", "%20"));
    }
}
