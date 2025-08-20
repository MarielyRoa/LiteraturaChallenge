package com.example.LiteraturaChallenge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ILibroRepo extends JpaRepository<Libros, Long> {
    Libros findByTitulo(String titulo); // Buscar libro por título
}
