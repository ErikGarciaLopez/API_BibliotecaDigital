package com.example.biblioteca.erik.repository;

import com.example.biblioteca.erik.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAutorRepository extends JpaRepository<Autor, Long> {

}
