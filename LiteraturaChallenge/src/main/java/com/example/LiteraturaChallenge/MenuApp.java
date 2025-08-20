package com.example.LiteraturaChallenge;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class MenuApp {

    private final BibliotecaService bibliotecaService;
    private Scanner teclado = new Scanner(System.in);

    public MenuApp(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    public void mostrarMenu() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n===== BIBLIOTECA =====");
            System.out.println("1. Buscar y guardar libro por título");
            System.out.println("2. Lista de libros en BD");
            System.out.println("3. Lista de autores en BD");
            System.out.println("4. Autores vivos por año");
            System.out.println("5. Salir");
            System.out.print("Opción: ");

            String opcion = teclado.nextLine();

            switch (opcion) {
                case "1" -> buscarLibro();
                case "2" -> listaLibros();
                case "3" -> listaAutores();
                case "4" -> autoresPorAnio();
                case "5" -> continuar = false;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void buscarLibro() {
        System.out.print("Ingrese título: ");
        String titulo = teclado.nextLine();
        Libros libro = bibliotecaService.guardarLibroDesdeAPI(titulo);
        if (libro != null) {
            System.out.println("Libro guardado: " + libro);
        } else {
            System.out.println("No se encontró el libro.");
        }
    }

    private void listaLibros() {
        List<Libros> libros = bibliotecaService.listarLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros en la BD.");
            return;
        }
        System.out.println("\n===== LISTA DE LIBROS =====");
        int i = 1;
        for (Libros libro : libros) {
            System.out.println(i + ". " + libro.getTitulo());
            i++;
        }
    }

    private void listaAutores() {
        List<Autores> autores = bibliotecaService.listarAutores();
        if (autores.isEmpty()) {
            System.out.println("No hay autores en la BD.");
            return;
        }
        System.out.println("\n===== LISTA DE AUTORES =====");
        int i = 1;
        for (Autores autor : autores) {
            System.out.println(i + ". " + autor.getNombre());
            i++;
        }
    }

    private void autoresPorAnio() {
        System.out.print("Ingrese año: ");
        int anio = Integer.parseInt(teclado.nextLine());
        List<Autores> autores = bibliotecaService.autoresPorAnio(anio);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en " + anio);
            return;
        }
        System.out.println("\n===== AUTORES VIVOS EN " + anio + " =====");
        int i = 1;
        for (Autores autor : autores) {
            System.out.println(i + ". " + autor);
            i++;
        }
    }
}
