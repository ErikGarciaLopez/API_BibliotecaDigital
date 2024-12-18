package com.example.biblioteca.erik.controller;

import com.example.biblioteca.erik.model.Prestamo;
import com.example.biblioteca.erik.payload.response.ApiResponse;
import com.example.biblioteca.erik.service.IPrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PrestamoController {

    @Autowired
    private IPrestamoService prestamoService;

    @GetMapping("/prestamos")
    public List<Prestamo> getPrestamos() {
        return prestamoService.getPrestamos();
    }

    @PostMapping("/prestamos")
    public ResponseEntity<ApiResponse> savePrestamo(@RequestBody Prestamo prestamo) {
        try {
            Prestamo prestamoGuardado = prestamoService.savePrestamo(prestamo);

            ApiResponse response = ApiResponse.builder()
                    .status("SUCCESS")
                    .message("Prestamo creado exitosamente")
                    .data(prestamoGuardado)
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .status("ERROR")
                    .message("Error al crear el prestamo: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
