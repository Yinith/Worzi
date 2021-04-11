package com.example.lista;

import com.example.tarjeta.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Lista {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String nombre;
    
    // Relacion bidireccional: las tarjetas necesitan saber a qué lista pertenecen
 	@OneToMany(mappedBy = "listaAsociada", cascade = CascadeType.ALL, orphanRemoval = true,fetch=FetchType.EAGER)
    private List<Tarjeta> tarjetas;
     
 	
 	// Constructor
 	public Lista() {
 	} 	
 	
    public Lista(String nombre) {
		super();
		this.nombre = nombre;
		this.tarjetas = new ArrayList<Tarjeta>();
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

	public List<Tarjeta> getTarjetas() {
		return tarjetas;
	}

	public void setTarjetas(List<Tarjeta> tarjetas) {
		this.tarjetas = tarjetas;
	}

	// Metodo sobrecargado que permite obtener una tarjeta por su indice o directamente encontrando ese objeto 'Tarjeta'
	public Tarjeta getTarjeta(int index) {
		return this.tarjetas.get(index);
	}

	// Busca esa tarjeta en el ArrayList de tarjetas de la lista actual. Si no la encuentra, devuelve un null.
	public Tarjeta getTarjeta(Tarjeta t) {
		for (Tarjeta tarjeta: tarjetas) {
			if (tarjeta==t) {
				return tarjeta;
			}
		}
		return null;
	}

	// Metodo que añade una tarjeta al ArrayList, devuelve true si lo ha conseguido
	public boolean addTarjeta(Tarjeta t) {
		return this.tarjetas.add(t);
	}

	// Metodo que borra una tarjeta asociada a la lista. Sobrecargado.
	// Si la consigue borrar, devuelve el objeto lista. Si no lo consigue, devuelve null.
	public Tarjeta removeTarjeta(int index) {
		return this.tarjetas.remove(index);
	}
	
	// Si la consigue borrar, devuelve true. Si no lo consigue, devuelve false.
	public boolean removeTarjeta(Tarjeta t) {
		return this.tarjetas.remove(t);
	}
	
	// Devuelve cuántas tarjetas tiene dentro la lista en este momento
	public int size() {
		return this.tarjetas.size();
	}
	
	public Tarjeta getAll() {
		for (Tarjeta tarjeta: tarjetas) {
				return tarjeta;
			}
		return null;
	}

}
