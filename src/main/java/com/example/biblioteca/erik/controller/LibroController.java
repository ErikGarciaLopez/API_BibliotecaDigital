package com.example.biblioteca.erik.controller;

import com.example.biblioteca.erik.model.Autor;
import com.example.biblioteca.erik.model.Libro;
import com.example.biblioteca.erik.payload.response.ApiResponse;
import com.example.biblioteca.erik.service.ILibroService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/libro")
    public ResponseEntity<ApiResponse> saveLibro(@RequestBody Libro libro) {
        try {
            Libro libroGuardado = libroService.saveLibro(libro);

            ApiResponse response = ApiResponse.builder()
                    .status("SUCCESS")
                    .message("Libro creado exitosamente")
                    .data(libroGuardado)
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .status("ERROR")
                    .message("Error al crear el libro: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("libro/{id}")
    public ResponseEntity<ApiResponse> updateLibro(@PathVariable Long id, @RequestBody Libro libro){
        try {
            Libro libroActualizado = libroService.updateLibro(id, libro);

            ApiResponse response = ApiResponse.builder()
                    .status("SUCCESS")
                    .message("Libro actualizado exitosamente")
                    .data(libroActualizado)
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            ApiResponse response = ApiResponse.builder()
                    .status("ERROR")
                    .message(e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .status("ERROR")
                    .message("Error al actualizar el libro: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
