package com.example.biblioteca.erik.service;

import com.example.biblioteca.erik.model.Libro;
import com.example.biblioteca.erik.repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService implements ILibroService{

    @Autowired
    private ILibroRepository libroRepo;

    @Override
    public List<Libro> getLibros() {
        return libroRepo.findAll();
    }

    @Override
    public Optional<Libro> getLibroById(Long id) {
        return libroRepo.findById(id);
    }

    @Override
    public Libro saveLibro(Libro libro) {
        return libroRepo.save(libro);
    }
}
