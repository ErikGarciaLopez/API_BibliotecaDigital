package com.example.biblioteca.erik.repository;

import com.example.biblioteca.erik.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
}
