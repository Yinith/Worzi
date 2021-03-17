package com.example;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.lista.Lista;
import com.example.lista.ListaRepository;
import com.example.tablero.Tablero;
import com.example.tablero.TableroRepository;
import com.example.tarjeta.Tarjeta;
import com.example.tarjeta.TarjetaRepository;
import com.example.usuario.Usuario;
import com.example.usuario.UsuarioRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class Controlador {

	int numeroLista = 1;
	private Usuario usuarioActual;
	ArrayList<Lista> listasGlobal = new ArrayList<Lista>();

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private TableroRepository tableroRepository;
	@Autowired
	private ListaRepository listaRepository;
	@Autowired
	private TarjetaRepository tarjetaRepository;

	
	@GetMapping("/pagPrincipal")
	public String paginaInicial(Model model)
	{
		model.addAttribute("usu", usuarioActual);
	    return "main";
	}
	
	@GetMapping("/")
	public String main() {

		return "pagSesion";
	}
	
	@GetMapping("/registrarse")
	public String registrarse()
	{
		return "registrarse";
	}
	
	@PostMapping("/nuevoUsuario")
	public String registrarse(Model model, @RequestParam String nombreUsuario,
			@RequestParam String email, @RequestParam String contrasenya) {

		Usuario usuario = new Usuario(nombreUsuario, email, contrasenya);
		usuarioRepository.save(usuario);
		usuarioActual = usuario;
		model.addAttribute("usu", usuario);
			
		Tablero t = new Tablero("Tablero de "+usuario.getNombreUsuario(), usuario);
		usuario.addTablero(t);
		tableroRepository.save(t);
		model.addAttribute("tableros", usuario.getTableros());
		return "main";
	}

	@GetMapping("/iniciarSesion")
	public String iniciarSesion()
	{
		return "iniciarSesion";
	}
	
	@PostMapping("/usuarioAcceso")
	public String iniciarSesion(Model model, Usuario usuario, HttpSession sesion)
	{
		Optional<Usuario> opt = usuarioRepository.findByNombreUsuarioAndContrasenyaAndEmail(usuario.getNombreUsuario(), usuario.getContrasenya(), usuario.getEmail());
		if(opt.isPresent()) {
			Usuario usu = opt.get();
			usuarioActual = usu;
			model.addAttribute("usu", usu);
			model.addAttribute("tableros", usu.getTableros());
			return "main";
		}
		else {
			return "usuarioNoExiste";
		}

	}

	@GetMapping("/perfil")
	public String userProfileView(Model model, HttpSession sesion) {
		/*String username = "";
		
		Optional<Usuario> opt = usuarioRepository.findById(usuarioActual);
		if(opt.isPresent()) {
			Usuario user = opt.get();
			username = user.getNombreUsuario();
		}
		
		model.addAttribute("usuario", username);

		// prueba
		//List<Usuario> usuarioActual = usuarioRepository.findAll();
		model.addAttribute("usuario", usuarioActual);*/
		
		
	
		//model.addAttribute("usuario", usuarioActual);
		model.addAttribute("nombre", usuarioActual.getNombreUsuario());
		return "miPerfil";
	}

/*
	@PostMapping("/crearLista")
	public String crearLista(Model model, @RequestParam String nombre) {
	
		Lista l = new Lista(nombre);
		listasGlobal.add(l);
		listaRepository.save(l);
		
		model.addAttribute("listasGlobal", listasGlobal);
		
		return "main";
	}*/
	
	@GetMapping("/crearTablero")
	public String verTarjeta(Model model) {
		return "GetTablero";
	}

	@PostMapping("/Tablero")
	public String addTablero(Model model, @RequestParam String nombre,
			@RequestParam(required=false, defaultValue="") String fechaFin , @RequestParam(required=false, defaultValue="") String descripcion) {

		/*model.addAttribute("nombre", nombre);
		model.addAttribute("fecha", fechaFin);
		model.addAttribute("descripcion", descripcion);
		
		Lista lista = new Lista("Lista "+numeroLista);
		Tarjeta t = new Tarjeta(nombre, fechaFin, descripcion);

		model.addAttribute("tarjeta", t);
		lista.addTarjeta(t);

		List<Tarjeta> tar = tarjetaRepository.findByNombre(nombre);
		model.addAttribute("tarjeta", tar);

		// model.addAttribute("lista", lista);*/
		
		Tablero tablero = new Tablero(nombre);
		usuarioActual.addTablero(tablero);
		List<Lista> act = (List<Lista>) tableroRepository.save(tablero);

		return "main";
	}
	
	@GetMapping("/crearLista")
	public String crearLista(Model model) {
		return "GetLista";
	}

	@PostMapping("/Lista")
	public String addLista(Model model, @RequestParam String nombre,
			@RequestParam(required=false, defaultValue="") String fechaFin , @RequestParam(required=false, defaultValue="") String descripcion) {

		/*model.addAttribute("nombre", nombre);
		model.addAttribute("fecha", fechaFin);
		model.addAttribute("descripcion", descripcion);
		
		Lista lista = new Lista("Lista "+numeroLista);
		Tarjeta t = new Tarjeta(nombre, fechaFin, descripcion);

		model.addAttribute("tarjeta", t);
		lista.addTarjeta(t);

		List<Tarjeta> tar = tarjetaRepository.findByNombre(nombre);
		model.addAttribute("tarjeta", tar);

		// model.addAttribute("lista", lista);*/
		
//		Tarjeta tarjeta = new Tarjeta(nombre,fechaFin,descripcion, lista);
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("tableros", usuarioActual.getTableros());

		return "main";
	}
	
	
	@GetMapping("/crearTarjeta")
	public String crearTarjeta(Model model) {
		
		return "GetTarjeta";
	}
	
	@GetMapping("/borrar/tarjeta/{id}")
	public String borrarTarjeta(Model model, @PathVariable long id) {
		
		Optional<Tarjeta> opt = tarjetaRepository.findById(id);
		if(opt.isPresent()) {
			Tarjeta tarj = opt.get();
			Lista l = tarj.getListaAsociada();
			l.removeTarjeta(tarj);
			tarjetaRepository.delete(tarj);
		}
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("tableros", usuarioActual.getTableros());
		return "main";
	}
	
	

	Lista lista = new Lista("Lista "+numeroLista);
	
	@PostMapping("/Tarjeta")
	public String addTarjeta(Model model, @RequestParam String nombre,
			@RequestParam(required=false) String fechaFin , @RequestParam(required=false) String descripcion) {

		//model.addAttribute("nombre", nombre);
		//model.addAttribute("fecha", fechaFin);
		//model.addAttribute("descripcion", descripcion);
		
		
		//Tarjeta t = new Tarjeta(nombre, fechaFin, descripcion);

		//model.addAttribute("tarjeta", t);
		//lista.addTarjeta(t);

		//List<Tarjeta> tar = tarjetaRepository.findByNombre(nombre);
		//model.addAttribute("tarjeta", tar);

		// model.addAttribute("lista", lista);*/
		
		Tarjeta tarjeta = new Tarjeta(nombre,fechaFin,descripcion, lista);
		lista.addTarjeta(tarjeta);
		model.addAttribute("tarjeta", tarjeta);
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("lista", lista);

		return "main";
	}

}
