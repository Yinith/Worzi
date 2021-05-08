package com.example;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lista.Lista;
import com.example.tablero.Tablero;
import com.example.tarjeta.Tarjeta;
import com.example.tarjeta.TarjetaRepository;

@Service
public class ServicioTarjetas {
	
	@Autowired
	TarjetaRepository repositorio;
	
	public Collection<Tarjeta> getTarjetas() {
		return repositorio.findAll();
	}
	
	public Tarjeta getTarjetaById(long id) {
		return repositorio.findById(id).orElseThrow(null);
	}	
	public Tarjeta getTarjetaByNombre(String nombre) {
		return repositorio.findByNombre(nombre);
	}	

	public void borrarTarjeta(Tarjeta tarjeta) {
		repositorio.delete(tarjeta);
	}	

	public void borrarTarjetaById(long id) {
		repositorio.deleteById(id);
	}
	
	public void borrarTarjetaByNombre(String nombre) {
		repositorio.deleteByNombre(nombre);
	}
	
	public void borrarTodo() {
		repositorio.deleteAll();
	}
	
	public void guardarTarjeta(Tarjeta tarjeta) {
		repositorio.save(tarjeta);
	}
	public void guardarTarjetas(List<Tarjeta> tarjetas) {
		repositorio.saveAll(tarjetas);
	}
	
	
}
