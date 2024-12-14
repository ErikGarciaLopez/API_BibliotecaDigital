package com.example.biblioteca.erik.controller;

import com.example.biblioteca.erik.model.Autor;
import com.example.biblioteca.erik.payload.response.ApiResponse;
import com.example.biblioteca.erik.service.IAutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class AutorController {

    @Autowired
    private IAutorService autorService;

    @GetMapping ("/autores")
    public List<Autor> getAutores() {
        return autorService.getAutores();
    }

    @GetMapping("/autores/{id}")
    public ResponseEntity<ApiResponse> getAutorById(@PathVariable Long id) {
        try {
            Optional<Autor> autorOptional = autorService.getAutorById(id);

            if (autorOptional.isEmpty()) {
                ApiResponse errorResponse = ApiResponse.builder()
                        .status("ERROR")
                        .message("No se encontró el autor con ID: " + id)
                        .timestamp(LocalDateTime.now())
                        .build();

                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }

            ApiResponse response = ApiResponse.builder()
                    .status("SUCCESS")
                    .message("Autor encontrado exitosamente")
                    .data(autorOptional.get())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .status("ERROR")
                    .message("Error al buscar el autor: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
