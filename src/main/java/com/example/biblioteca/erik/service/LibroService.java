package com.example.biblioteca.erik.service;

import com.example.biblioteca.erik.model.Libro;
import com.example.biblioteca.erik.repository.ILibroRepository;
import jakarta.persistence.EntityNotFoundException;
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

    @Override
    public Libro updateLibro(Long id, Libro libroDetails) {
        Optional<Libro> libro = libroRepo.findById(id);

        if (libro.isEmpty()) {
            throw new EntityNotFoundException("No se encontró el libro con ID: " + id);
        }

        Libro libroExistente = libro.get();

        if (libroDetails.getTitulo() != null) {
            libroExistente.setTitulo(libroDetails.getTitulo());
        }
        if (libroDetails.getDescripcion() != null) {
            libroExistente.setDescripcion(libroDetails.getDescripcion());
        }
        if (libroDetails.getAutor() != null) {
            libroExistente.setAutor(libroDetails.getAutor());
        }
        if (libroDetails.getGenero() != null) {
            libroExistente.setGenero(libroDetails.getGenero());
        }

        libroExistente.setDisponible(libroDetails.isDisponible());

        return libroRepo.save(libroExistente);

    }
}
