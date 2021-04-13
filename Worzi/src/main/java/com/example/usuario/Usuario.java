package com.example.usuario;

import javax.persistence.*;

import com.example.tablero.Tablero;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique = true)
	private String nombreUsuario;

	@Column(unique = true)
	private String email;

	private String contrasenya;

	private boolean admin;
	
	// Relacion bidireccional: el tablero tambien va a saber a que usuario pertenece
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true,fetch=FetchType.EAGER)
	private List<Tablero> tableros;

	
	// Constructores
	public Usuario() {
	}
	
	public Usuario(String nombreUsuario, String email, String contrasenya) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.contrasenya = contrasenya;
		this.setAdmin(false);			// Si en la creacion no se indica nada, por defecto el usuario no es admin
		// this.tableros = new LinkedList<Tablero>();
		this.tableros = new ArrayList<Tablero>();
	}
	
	public Usuario(String nombreUsuario, String email, String contrasenya, boolean admin) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.contrasenya = contrasenya;
		setAdmin(admin);
		this.tableros = new ArrayList<Tablero>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public List<Tablero> getTableros(){
		return tableros;
	}
	
	
	// Metodo sobrecargado que permite obtener un tablero por su indice o directamente encontrando ese objeto en la lista
	public Tablero getTablero(int index) {
		return this.tableros.get(index);
	}
	
	// Busca ese tablero en el ArrayList de tableros del usuario. Si no lo encuentra, devuelve un null.
	public Tablero getTablero(Tablero t) {
		for (Tablero tablero : tableros) {
			if (tablero==t) {
				return tablero;
			}
		}
		return null;
	}
	
	// Metodo que añade un tablero al ArrayList de tableros, devuelve true si lo ha conseguido
	public boolean addTablero(Tablero t) {
		return this.tableros.add(t);
	}
	
	// Metodo que borra un tablero del ArrayLst de tablero. Sobrecargado.
	// Si lo consigue borrar, devuelve el tablero. Si no lo consigue, devuelve null.
	public Tablero removeTablero(int index) {
		return this.tableros.remove(index);
	}
	// Si lo consigue borrar, devuelve true. Si no lo consigue, devuelve false.
	public boolean removeTablero(Tablero t) {
		return this.tableros.remove(t);
	}
	
	

}
