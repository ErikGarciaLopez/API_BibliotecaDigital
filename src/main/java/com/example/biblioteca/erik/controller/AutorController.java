package com.example.biblioteca.erik.controller;

import com.example.biblioteca.erik.model.Autor;
import com.example.biblioteca.erik.payload.response.ApiResponse;
import com.example.biblioteca.erik.service.IAutorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
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

    @PostMapping("/autores")
    public ResponseEntity<ApiResponse> saveAutor(@RequestBody Autor autor) {
        try {
            Autor autorGuardado = autorService.saveAutor(autor);

            ApiResponse response = ApiResponse.builder()
                    .status("SUCCESS")
                    .message("Autor creado exitosamente")
                    .data(autorGuardado)
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .status("ERROR")
                    .message("Error al crear el autor: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/autores/{id}")
    public ResponseEntity<ApiResponse> updateAutor(@PathVariable Long id, @RequestBody Autor autor) {
        try {
            Autor autorActualizado = autorService.updateAutor(id, autor);

            ApiResponse response = ApiResponse.builder()
                    .status("SUCCESS")
                    .message("Autor actualizado exitosamente")
                    .data(autorActualizado)
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
                    .message("Error al actualizar el autor: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/autores/{id}")
    public ResponseEntity<ApiResponse> deleteAutor(@PathVariable Long id) {
        try {
            // Verificar si el autor existe
            Optional<Autor> autorExistente = autorService.getAutorById(id);

            if (autorExistente.isEmpty()) {
                ApiResponse response = ApiResponse.builder()
                        .status("ERROR")
                        .message("No se puede eliminar: el autor con ID " + id + " no existe")
                        .timestamp(LocalDateTime.now())
                        .build();

                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Si existe, eliminamos
            autorService.deleteAutor(id);

            ApiResponse response = ApiResponse.builder()
                    .status("SUCCESS")
                    .message("Autor eliminado exitosamente")
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .status("ERROR")
                    .message("Error al eliminar el autor: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... resto del código ...

    @PostMapping("/autores/buscar-o-crear")
    public ResponseEntity<ApiResponse> buscarOCrearAutor(@RequestBody Autor autor) {
        try {
            Autor autorEncontrado = autorService.buscarOCrearAutor(autor.getNombre());

            ApiResponse response = ApiResponse.builder()
                    .status("SUCCESS")
                    .message(autorEncontrado.getId() != null ? "Autor encontrado exitosamente" : "Autor creado exitosamente")
                    .data(autorEncontrado)
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .status("ERROR")
                    .message("Error al buscar o crear el autor: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
