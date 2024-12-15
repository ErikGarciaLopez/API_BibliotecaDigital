package com.example.biblioteca.erik.service;

import com.example.biblioteca.erik.model.Autor;
import com.example.biblioteca.erik.repository.IAutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService implements IAutorService{

    @Autowired
    private IAutorRepository autoRepo;

    @Override
    public List<Autor> getAutores() {
        return autoRepo.findAll();
    }

    @Override
    public Optional<Autor> getAutorById(Long id) {
        return autoRepo.findById(id);
    }

    @Override
    public Autor saveAutor(Autor autor) {
        return autoRepo.save(autor);
    }

    @Override
    public Autor updateAutor(Long id, Autor autorDetails) {
        Optional<Autor> autor = autoRepo.findById(id);

        if (autor.isEmpty()) {
            throw new EntityNotFoundException("No se encontró el autor con ID: " + id);
        }

        Autor autorExistente = autor.get();

        // Actualizamos solo los campos no nulos
        if (autorDetails.getNombre() != null) {
            autorExistente.setNombre(autorDetails.getNombre());
        }
        if (autorDetails.getNacionalidad() != null) {
            autorExistente.setNacionalidad(autorDetails.getNacionalidad());
        }
        if (autorDetails.getBiografia() != null) {
            autorExistente.setBiografia(autorDetails.getBiografia());
        }

        return autoRepo.save(autorExistente);
    }

    @Override
    public void deleteAutor(Long id) {
        autoRepo.deleteById(id);
    }
}
