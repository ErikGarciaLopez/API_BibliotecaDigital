package com.example.biblioteca.erik.controller;

import com.example.biblioteca.erik.model.Libro;
import com.example.biblioteca.erik.payload.response.ApiResponse;
import com.example.biblioteca.erik.service.ILibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class LibroController {

    @Autowired
    private ILibroService libroService;

    @GetMapping("/libro")
    public List<Libro> getLibros() {
        return libroService.getLibros();
    }

    @GetMapping("/libro/{id}")
    public ResponseEntity<ApiResponse> getLibroById(@PathVariable Long id){
        try {
            Optional<Libro> libroOptional = libroService.getLibroById(id);

            if (libroOptional.isEmpty()) {
                ApiResponse errorResponse = ApiResponse.builder()
                        .status("ERROR")
                        .message("No se encontró el libro con ID: " + id)
                        .timestamp(LocalDateTime.now())
                        .build();

                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }

            ApiResponse response = ApiResponse.builder()
                    .status("SUCCESS")
                    .message("Libro encontrado exitosamente")
                    .data(libroOptional.get())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .status("ERROR")
                    .message("Error al buscar el libro: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }



}
