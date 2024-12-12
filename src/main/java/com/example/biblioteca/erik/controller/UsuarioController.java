package com.example.biblioteca.erik.controller;

import com.example.biblioteca.erik.model.Usuario;
import com.example.biblioteca.erik.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/usuarios/traer")
    public List<Usuario> getUsuarios() {
        return usuarioService.getUsuarios();
    }

}
