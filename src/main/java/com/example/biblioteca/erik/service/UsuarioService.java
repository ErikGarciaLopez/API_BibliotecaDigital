package com.example.biblioteca.erik.service;

import com.example.biblioteca.erik.model.Usuario;
import com.example.biblioteca.erik.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService{

    @Autowired
    private IUsuarioRepository usuarioRepo;

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioRepo.findAll();
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        usuarioRepo.save(usuario);
        return usuario;
    }

}
