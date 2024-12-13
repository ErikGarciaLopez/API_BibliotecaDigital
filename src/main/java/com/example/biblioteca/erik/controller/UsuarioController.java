package com.example.biblioteca.erik.controller;

import com.example.biblioteca.erik.model.Usuario;
import com.example.biblioteca.erik.payload.response.ApiResponse;
import com.example.biblioteca.erik.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/usuarios/traer")
    public List<Usuario> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @PostMapping ("/usuarios/crear")
    public ResponseEntity<ApiResponse> saveUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioGuardado = usuarioService.saveUsuario(usuario);

            ApiResponse response = ApiResponse.builder()
                    .status("SUCCESS")
                    .message("Usuario creado exitosamente")
                    .data(usuarioGuardado)
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .status("ERROR")
                    .message("Error al crear el usuario: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
