package com.example.LiteraturaChallenge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BibliotecaService {

    private final IAutorRepo autorRepo;
    private final ILibroRepo libroRepo;
    private final obtenerDatos apiService;

    // Constructor con inyección de dependencias
    public BibliotecaService(IAutorRepo autorRepo, ILibroRepo libroRepo, obtenerDatos apiService) {
        this.autorRepo = autorRepo;
        this.libroRepo = libroRepo;
        this.apiService = apiService;
    }

    // 🔹 Guardar libro en BD desde API
    public Libros guardarLibroDesdeAPI(String titulo) {
        try {
            String json = apiService.buscarLibroPorTitulo(titulo);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode results = root.get("results");

            if (results == null || results.isEmpty()) return null;

            JsonNode libroNode = results.get(0); // tomo el primero
            String tituloLibro = libroNode.get("title").asText();

            // Evitar duplicados
            Libros existente = libroRepo.findByTitulo(tituloLibro);
            if (existente != null) return existente;

            // Autor
            JsonNode authorsArray = libroNode.get("authors");
            Autores autor = null;
            if (authorsArray != null && authorsArray.isArray() && authorsArray.size() > 0) {
                JsonNode autorNode = authorsArray.get(0);
                String nombreAutor = autorNode.get("name").asText();
                Integer nacimiento = autorNode.has("birth_year") && !autorNode.get("birth_year").isNull()
                        ? autorNode.get("birth_year").asInt() : null;
                Integer muerte = autorNode.has("death_year") && !autorNode.get("death_year").isNull()
                        ? autorNode.get("death_year").asInt() : null;

                autor = autorRepo.findByNombre(nombreAutor);
                if (autor == null) {
                    autor = new Autores();
                    autor.setNombre(nombreAutor);
                    autor.setFechaNacimiento(nacimiento);
                    autor.setFechaMuerte(muerte);
                    autorRepo.save(autor);
                }
            }

            // Libro
            Libros libro = new Libros();
            libro.setTitulo(tituloLibro);
            libro.setAutor(autor);
            libro.setResumen(libroNode.has("summaries") && !libroNode.get("summaries").isNull()
                    ? libroNode.get("summaries").toString() : null);
            libro.setCategorias(libroNode.has("bookshelves") && !libroNode.get("bookshelves").isNull()
                    ? libroNode.get("bookshelves").toString() : null);
            libro.setDescargas(libroNode.has("download_count") && !libroNode.get("download_count").isNull()
                    ? libroNode.get("download_count").asInt() : 0);

            List<String> lenguajes = new ArrayList<>();
            if (libroNode.has("languages") && libroNode.get("languages").isArray()) {
                libroNode.get("languages").forEach(lang -> lenguajes.add(lang.asText()));
            }
            libro.setLenguajes(lenguajes);

            return libroRepo.save(libro);

        } catch (Exception e) {
            throw new RuntimeException("Error procesando libro: " + e.getMessage(), e);
        }
    }

    // 🔹 Listar todos los libros en BD
    public List<Libros> listarLibros() {
        return libroRepo.findAll();
    }

    // 🔹 Listar todos los autores en BD
    public List<Autores> listarAutores() {
        return autorRepo.findAll();
    }

    // 🔹 Autores vivos en un año
    public List<Autores> autoresPorAnio(int anio) {
        return autorRepo.findAll().stream()
                .filter(a -> (a.getFechaNacimiento() != null && a.getFechaNacimiento() <= anio) &&
                        (a.getFechaMuerte() == null || a.getFechaMuerte() >= anio))
                .toList();
    }
}
