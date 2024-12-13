package com.example.biblioteca.erik.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

}