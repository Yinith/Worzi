package com.example.tablero;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.example.lista.Lista;
import com.example.tarjeta.Tarjeta;
import com.example.usuario.Usuario;

@Entity
public class Tablero {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@Column(unique = true)
	private String nombre;
	
	private boolean simple;
	private String fondo;
	private String descripcion;
	
	// El tablero sí que sabe qué usuario es su dueño (posteriormente tal vez añadamos usuarios 'invitados')
	@ManyToOne
	private Usuario owner;
	
	// Relacion unidireccional: las listas no necesitan saber a qué tablero pertenecen
	@OneToMany(orphanRemoval = true)
	private List<Lista> listas;
	 
	//Constructor
	public Tablero() {
		super();
	}
	
	public Tablero(String nombre) {
		super();
		this.nombre = nombre;
		this.simple = true;
		this.fondo = "";
		this.descripcion = "";
		this.listas = new ArrayList<Lista>();
	}
	
	public Tablero(String nombre, Usuario creador) {
		super();
		this.nombre = nombre;
		this.simple = true;
		this.fondo = "";
		this.descripcion = "";
		this.owner = creador;
		this.listas = new ArrayList<Lista>();
	}
	
	public Tablero(String nombre, boolean simple, String fondo, String descripcion, Usuario creador) {
		super();
		this.nombre = nombre;
		this.simple = simple;
		this.fondo = fondo;
		this.descripcion = descripcion;
		this.owner = creador;
		this.listas = new ArrayList<Lista>();
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

	public boolean isSimple() {
		return simple;
	}

	public void setSimple(boolean simple) {
		this.simple = simple;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Usuario getOwner() {
		return this.owner;
	}

	public void setOwner(Usuario owner) {
		this.owner = owner;
	}
	
	public List<Lista> getListas() {
		return this.listas;
	}

	public void setListas(List<Lista> listas) {
		this.listas = listas;
	}
	
	// Metodo sobrecargado que permite obtener una lista por su indice o directamente encontrando ese objeto 'Lista'
	public Lista getLista(int index) {
		return this.listas.get(index);
	}
	
	// Busca esa lista en el ArrayList de listas del tablero. Si no la encuentra, devuelve un null.
	public Lista getLista(Lista l) {
		for (Lista lista: listas) {
			if (lista==l) {
				return lista;
			}
		}
		return null;
	}
	
	// Metodo que añade una lista al ArrayList, devuelve true si lo ha conseguido
	public boolean addLista(Lista l) {
		return this.listas.add(l);
	}
	
	// Metodo que borra una lista asociada. Sobrecargado.
	// Si la consigue borrar, devuelve el objeto lista. Si no lo consigue, devuelve null.
	public Lista removeLista(int index) {
		return this.listas.remove(index);
	}
	// Si la consigue borrar, devuelve true. Si no lo consigue, devuelve false.
	public boolean removeLista(Lista l) {
		return this.listas.remove(l);
	}
	
	// Para tableros con varias listas,
	// este metodo permite cambiar una tarjeta de una lista a otra.
	public void moveTarjeta(Tarjeta t, Lista origen, Lista destino) {
		origen.removeTarjeta(t);
		destino.addTarjeta(t);
	}
	
	
}
