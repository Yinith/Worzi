package com.example.tarjeta;


import java.util.List;

import javax.persistence.CascadeType;

// import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.example.lista.Lista;

@Entity
public class Tarjeta {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nombre;
    private String fechaFin;
    private String color;
    private String descripcion;
    private String checklist;
    
    
    @ManyToOne
    private Lista listaAsociada;

    // Constructores
    public Tarjeta() {
    }
    public Tarjeta(String nombre, String fecha, String color, String descripcion, Lista lista, String checklist) {
		super();
		this.nombre = nombre;
		this.fechaFin = fecha;
		this.color = color;
		this.descripcion = descripcion;
		this.listaAsociada = lista;
		this.checklist = checklist;
	}
	public Tarjeta(String nombre, String fecha, String color, String descripcion, Lista lista) {
		super();
		this.nombre = nombre;
		this.fechaFin = fecha;
		this.color = color;
		this.descripcion = descripcion;
		this.listaAsociada = lista;
	}
	public Tarjeta(String nombre, String fecha, String descripcion, Lista lista) {
		super();
		this.nombre = nombre;
		this.fechaFin = fecha;
		this.color = "blanco";
		this.descripcion = descripcion;
		this.listaAsociada = lista;
	}
	public Tarjeta(String nombre, String fecha, Lista lista) {
		super();
		this.nombre = nombre;
		this.fechaFin = fecha;
		this.color = "blanco";
		this.descripcion = "";
		this.listaAsociada = lista;
	}
	public Tarjeta(String nombre, Lista lista) {
		super();
		this.nombre = nombre;
		this.fechaFin = "";
		this.color = "blanco";
		this.descripcion = "";
		this.listaAsociada = lista;
	}
	
	public long getId() {
		return id;
	}
	


	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Lista getListaAsociada() {
		return listaAsociada;
	}

	public void setListaAsociada(Lista listaAsociada) {
		this.listaAsociada = listaAsociada;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fecha) {
		this.fechaFin = fecha;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
    
}
