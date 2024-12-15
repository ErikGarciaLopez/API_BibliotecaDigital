package com.example.biblioteca.erik.service;

import com.example.biblioteca.erik.model.Autor;
import com.example.biblioteca.erik.repository.IAutorRepository;
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

    //Update


    @Override
    public void deleteAutor(Long id) {
        autoRepo.deleteById(id);
    }
}
