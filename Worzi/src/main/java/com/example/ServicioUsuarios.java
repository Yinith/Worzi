package com.example;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usuario.Usuario;
import com.example.usuario.UsuarioRepository;

@Service
public class ServicioUsuarios {
	
	@Autowired
	private UsuarioRepository repositorio;
	
	public Collection<Usuario> getUsuarios() {
		return repositorio.findAll();
	}
	
	public Usuario getUsuario(long id) {
		return repositorio.findById(id).orElseThrow(null);		
	}
	
	public Usuario getUsuarioByNombre(String nombreUsuario) {
		return repositorio.findByNombreUsuario(nombreUsuario).orElseThrow(null);		
	}
	
	public Usuario getUsuarioByCampos(String nombreUsuario, String contrasenya, String email) {
		return repositorio.findByNombreUsuarioAndContrasenyaAndEmail(nombreUsuario,contrasenya,email);		
	}
	
	public void borrarUsuario(long id) {
		Usuario usu = repositorio.findById(id).orElseThrow(null);
		repositorio.delete(usu);
	}
	
	public void guardarUsuario(Usuario usu) {
		repositorio.save(usu);
	}
	
	public boolean existeUsuario(Usuario usu) {
		return repositorio.existsByNombreUsuarioAndContrasenyaAndEmail(usu.getNombreUsuario(), usu.getContrasenya(), usu.getEmail());
	}
	
}
