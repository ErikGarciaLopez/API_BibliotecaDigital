package com.example.biblioteca.erik.service;

import com.example.biblioteca.erik.model.Prestamo;
import com.example.biblioteca.erik.repository.IPrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoService implements IPrestamoService{

    @Autowired
    private IPrestamoRepository prestamoRepo;

    @Override
    public List<Prestamo> getPrestamos() {
        return prestamoRepo.findAll();
    }

    @Override
    public Prestamo savePrestamo(Prestamo prestamo) {
        return prestamoRepo.save(prestamo);
    }
}
