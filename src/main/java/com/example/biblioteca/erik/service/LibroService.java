package com.example.biblioteca.erik.service;

import com.example.biblioteca.erik.model.Libro;
import com.example.biblioteca.erik.repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService implements ILibroService{

    @Autowired
    private ILibroRepository libroRepo;

    @Override
    public List<Libro> getLibros() {
        return libroRepo.findAll();
    }
}
