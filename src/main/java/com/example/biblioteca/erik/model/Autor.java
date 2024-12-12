package com.example.biblioteca.erik.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String nacionalidad;

    private String biografia;

}
