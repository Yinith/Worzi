package com.example.lista;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface ListaRepository extends JpaRepository<Lista, Long> {

    Optional<Lista> findByNombre(String nombreLista);

   

}