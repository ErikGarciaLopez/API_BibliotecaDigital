package com.example.biblioteca.erik.repository;

import com.example.biblioteca.erik.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPrestamoRepository extends JpaRepository<Prestamo, Long> {
}
