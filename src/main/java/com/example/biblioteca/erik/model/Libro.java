package com.example.biblioteca.erik.model;

import com.example.biblioteca.erik.utils.enums.Genero;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data // Incluye @Getter, @Setter, @ToString, @EqualsAndHashCode
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;

    @Column(nullable = false)
    private boolean disponible;
}