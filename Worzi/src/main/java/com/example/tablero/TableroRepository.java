package com.example.tablero;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.usuario.Usuario;



public interface TableroRepository extends JpaRepository<Tablero, Long> {

    Tablero findByNombre(String nombre);

	Tablero findByOwner(Usuario usu);


}