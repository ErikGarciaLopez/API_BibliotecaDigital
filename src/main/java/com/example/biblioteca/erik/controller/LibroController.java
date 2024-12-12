package com.example.biblioteca.erik.controller;

import com.example.biblioteca.erik.model.Libro;
import com.example.biblioteca.erik.service.ILibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LibroController {

    @Autowired
    private ILibroService libroService;

    @GetMapping("libro/traer")
    public List<Libro> getLibros() {
        return libroService.getLibros();
    }




}
