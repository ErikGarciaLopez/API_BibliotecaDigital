package com.example.biblioteca.erik.repository;

import com.example.biblioteca.erik.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILibroRepository extends JpaRepository<Libro, Long> {
}
