package com.example.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	//Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    //Optional<Usuario> findByNombreUsuarioAndContrasenyaAndEmail(String nombreUsuario, String contrasenya, String email);
    
    Optional <Usuario> findByNombreUsuario(String nombreUsuario);
    
	boolean existsByNombreUsuarioAndContrasenyaAndEmail(String nombreUsuario, String contrasenya, String email);
	
	Usuario findByNombreUsuarioAndContrasenyaAndEmail(String nombreUsuario, String contrasenya, String email);

}