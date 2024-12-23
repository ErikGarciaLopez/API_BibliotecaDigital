package com.example.biblioteca.erik.service;

import com.example.biblioteca.erik.model.Autor;

import java.util.List;
import java.util.Optional;

public interface IAutorService {

    public List<Autor> getAutores();
    public Optional<Autor> getAutorById(Long id);
    public Autor saveAutor(Autor autor);
    public Autor updateAutor(Long id, Autor autor);
    public void deleteAutor(Long id);
    Autor buscarOCrearAutor(String nombre);

}
