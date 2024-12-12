package com.example.biblioteca.erik.controller;

import com.example.biblioteca.erik.model.Prestamo;
import com.example.biblioteca.erik.service.IPrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PrestamoController {

    @Autowired
    private IPrestamoService prestamoService;

    @GetMapping("/prestamos/traer")
    public List<Prestamo> getPrestamos() {
        return prestamoService.getPrestamos();
    }

}
