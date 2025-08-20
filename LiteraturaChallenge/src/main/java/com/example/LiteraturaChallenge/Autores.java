package com.example.LiteraturaChallenge;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "autores")
public class Autores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental en la BD
    private Long id;

    @JsonAlias("name")
    @Column(nullable = false) // no puede ser nulo
    private String nombre;

    @JsonAlias("birth_year")
    private Integer fechaNacimiento;

    @JsonAlias("death_year")
    private Integer fechaMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Libros> libros;


    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(Integer fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public java.util.List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(java.util.List<Libros> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Autor {" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nacimiento=" + fechaNacimiento +
                ", muerte=" + fechaMuerte +
                '}';
    }
}
