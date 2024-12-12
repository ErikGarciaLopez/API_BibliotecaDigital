package com.example.biblioteca.erik.controller;

import com.example.biblioteca.erik.model.Autor;
import com.example.biblioteca.erik.service.IAutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AutorController {

    @Autowired
    private IAutorService autorService;

    @GetMapping ("/autor/traer")
    public List<Autor> getAutores() {
        return autorService.getAutores();
    }

}
