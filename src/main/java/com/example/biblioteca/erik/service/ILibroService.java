package com.example.biblioteca.erik.service;

import com.example.biblioteca.erik.model.Libro;

import java.util.List;
import java.util.Optional;

public interface ILibroService {

    public List<Libro> getLibros();
    public Optional<Libro> getLibroById(Long id);

}
