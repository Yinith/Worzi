package com.example;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lista.Lista;
import com.example.lista.ListaRepository;
import com.example.tablero.Tablero;
import com.example.usuario.Usuario;

@Service
public class ServicioListas {
	
	@Autowired
	ListaRepository repositorio;
	
	public Collection<Lista> getListas() {
		return repositorio.findAll();
	}
	
	public Lista getListaById(long id) {
		return repositorio.findById(id).orElseThrow();
	}	
	
	public Lista getListaByNombre(String nombre) {
		return repositorio.findByNombre(nombre).orElseThrow();
	}	


	public void borrarLista(Lista list) {
		repositorio.delete(list);
	}	

	public void borrarListaById(long id) {
		repositorio.deleteById(id);
	}
	public void borrarTodo() {
		repositorio.deleteAll();
	}
	
	public void guardarLista(Lista list) {
		repositorio.save(list);
	}
	public void guardarListas(List<Lista> lists) {
		repositorio.saveAll(lists);
	}
	
	public boolean existeTablero(Tablero tab) {
		return repositorio.existsById(tab.getId());
	}
	
}
