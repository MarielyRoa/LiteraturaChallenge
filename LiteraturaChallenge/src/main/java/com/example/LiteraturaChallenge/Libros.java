package com.example.LiteraturaChallenge;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "libros")
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonAlias("title")
    @Column(nullable = false)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autores autor;

    @JsonAlias("summaries")
    @Column(length = 2000)
    private String resumen;

    @JsonAlias("bookshelves")
    private String categorias;

    @JsonAlias("languages")
    @ElementCollection // porque es una lista de strings
    @CollectionTable(name = "libros_lenguajes", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "lenguaje")
    private List<String> lenguajes;

    @JsonAlias("download_count")
    private Integer descargas;

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autores getAutor() {
        return autor;
    }

    public void setAutor(Autores autor) {
        this.autor = autor;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public List<String> getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(List<String> lenguajes) {
        this.lenguajes = lenguajes;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return "Libro {" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor=" + (autor != null ? autor.getNombre() : "Desconocido") +
                ", resumen='" + resumen + '\'' +
                ", categorias='" + categorias + '\'' +
                ", lenguajes=" + lenguajes +
                ", descargas=" + descargas +
                '}';
    }
}
