package com.example;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tablero.Tablero;
import com.example.tablero.TableroRepository;
import com.example.usuario.Usuario;

@Service
public class ServicioTableros {
	
	@Autowired
	private TableroRepository repositorio;
	
	public Collection<Tablero> getTableros() {
		return repositorio.findAll();
	}
	
	public Tablero getTableroByNombre(String nombre) {
		return repositorio.findByNombre(nombre);
	}	
	
	public Tablero getTableroByOwner(Usuario usu) {
		return repositorio.findByOwner(usu);
	}	
	
	public Tablero getTableroById(long id) {
		return repositorio.findById(id).orElseThrow(null);
	}	

	public void borrarTablero(Tablero tab) {
		repositorio.delete(tab);
	}	

	public void borrarTableroById(long id) {
		repositorio.deleteById(id);
	}
	public void borrarTodo() {
		repositorio.deleteAll();
	}
	
	public void guardarTablero(Tablero tab) {
		repositorio.save(tab);
	}
	public void guardarTableros(List<Tablero> tabs) {
		repositorio.saveAll(tabs);
	}
	
	public boolean existeTablero(Tablero tab) {
		return repositorio.existsById(tab.getId());
	}
	
}
