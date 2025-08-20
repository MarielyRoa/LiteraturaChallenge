package com.example.LiteraturaChallenge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAutorRepo extends JpaRepository<Autores, Long> {
    Autores findByNombre(String nombre); // Buscar autor por nombre
}
