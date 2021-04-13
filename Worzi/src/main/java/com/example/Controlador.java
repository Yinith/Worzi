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

//	private Usuario usuarioActual;
	private long userID;

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private TableroRepository tableroRepository;
	@Autowired
	private ListaRepository listaRepository;
	@Autowired
	private TarjetaRepository tarjetaRepository;
	@Autowired
	private ServicioUsuarios servicioUsuarios;
	@Autowired
	private ServicioTableros servicioTableros;
	@Autowired
	private ServicioListas servicioListas;
	@Autowired
	private ServicioTarjetas servicioTarjetas;
	
	//PAGINA DE INICIO
	
	@GetMapping("/")
	public String main() 
	{
		return "pagSesion";
	}
	
	
	// GET LOGIN REGISTRO USUARIO
	
	@GetMapping("/registrarse")
	public String registrarse()
	{
		return "registrarse";
	}
	
	
	// POST USUARIO NUEVO
	
	@PostMapping("/nuevoUsuario")
	public String registrarse(Model model, Usuario usuario, HttpSession sesion)
	{
		if(usuario.getNombreUsuario().trim().equals("") || usuario.getContrasenya().trim().equals("") || usuario.getEmail().trim().equals(""))
		{
			
			return "error";
		}
		else
		{
			if(!servicioUsuarios.existeUsuario(usuario))
			{
				servicioUsuarios.guardarUsuario(usuario);
				//Usuario usuarioActual = usuario;
				model.addAttribute("usu", usuario);
				sesion.setAttribute("usuarioActual", usuario);
				return "main";
			}
			else
			{
				
				return "error";
			}
		}
	}
	
	
	// GET LOGIN ACCESO
	
	@GetMapping("/iniciarSesion")
	public String iniciarSesion()
	{
		return "iniciarSesion";
	}
	
	
	// POST USUARIO ACCESO
	
	@PostMapping("/usuarioAcceso")
	public String iniciarSesion(Model model, Usuario usuario, HttpSession sesion)
	{
		if(usuario.getNombreUsuario().trim().equals("") || usuario.getContrasenya().trim().equals("") || usuario.getEmail().trim().equals(""))
		{
			
			return "error";
		}
		else
		{
			Usuario usu = servicioUsuarios.getUsuarioByCampos(usuario.getNombreUsuario(), usuario.getContrasenya(), usuario.getEmail());
			if(servicioUsuarios.existeUsuario(usu))
			{
				//model.addAttribute("usu", usu);
				sesion.setAttribute("usuarioActual", usu);
				Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
				model.addAttribute("usu", usuarioActual);
				model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
				//model.addAttribute("tableros", usuarioActual.getTableros());
				model.addAttribute("tableros", servicioTableros.getTableroByOwner(usuarioActual));
				return "main";
			}
			else
			{

				return "error";
			}
		}
	}
	
	
	// PAGINA PRINCIPAL
	
	@GetMapping("/pagPrincipal")
	public String paginaInicial(Model model, HttpSession sesion, HttpServletRequest request)
	{
		// Es imprescindible buscarlo en el repositorio para comprobar que existe y cargar los datos actualizados
		//Optional<Usuario> opt = usuarioRepository.findById(userID);
		//Usuario usu = opt.get();
		//model.addAttribute("usu", usu);
		//model.addAttribute("tableros", usu.getTableros());
		
		//String nombre = request.getUserPrincipal().getName();
		//Usuario usuarioActual = servicioUsuarios.getUsuarioByNombre(nombre);
		//sesion.setAttribute("usuarioActual", usuarioActual);
		//model.addAttribute("tableros", usuarioActual.getTableros());

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		//List<Tablero> tableros = usuarioActual.getTableros();
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
		//model.addAttribute("tableros", usuarioActual.getTableros());
		//model.addAttribute("tableros", tableros);
		//model.addAttribute("tableros", usuarioActual.getTableros());
		model.addAttribute("tableros", servicioTableros.getTableroByOwner(usuarioActual));
		
	    return "main";
	}
	
	
	// PERFIL DEL USUARIO
	
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

	
		// Es imprescindible buscarlo en el repositorio para comprobar que existe y cargar los datos actualizados
		/*Optional<Usuario> opt = usuarioRepository.findById(userID);
		Usuario usu = opt.get();
		
		
		model.addAttribute("nombre", usu.getNombreUsuario());*/
		
		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
		model.addAttribute("tableros", servicioTableros.getTableroByOwner(usuarioActual));
		//model.addAttribute("tableros", servicioTableros.getTableros());
		//model.addAttribute("nombre", tablero.getNombre());
		return "miPerfil";
	}
	
	// METODO BORRADO DE CUENTAS
	
	@GetMapping("/borrarCuenta")
	public String accountDELETE(Model model, HttpSession sesion) {
		
		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		servicioUsuarios.borrarUsuario(usuarioActual.getId());
		
		return "pagSesion";
	}
	
	
	// GET PAGINA CREACION TABLERO
	
	@GetMapping("/crearTablero")
	public String crearTablero(Model model) {
		return "GetTablero";
	}
	
	
	// POST CREACION TABLERO
	
	@PostMapping("/Tablero")
	public String addTablero(Model model, @RequestParam String nombre,
			@RequestParam(required=false, defaultValue="") String fechaFin , @RequestParam(required=false, defaultValue="") String descripcion, HttpSession sesion) {

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
		
		//tablero.setOwner(usuarioActual);
		//usuarioActual.addTablero(tablero);
		//tableroRepository.save(tablero);
		//model.addAttribute("usuarioActual", usuarioActual.getNombreUsuario());
		
		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		
		Tablero tablero = new Tablero(nombre,usuarioActual);
		servicioTableros.guardarTablero(tablero);
		
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
		model.addAttribute("tableros", servicioTableros.getTableroByOwner(usuarioActual));
		//model.addAttribute("tableros", servicioTableros.getTableros());
		//model.addAttribute("nombre", tablero.getNombre());
		return "main";
	}
	
		
	@GetMapping("/borrarTablero/{id}")
	public String addTablero(Model model, @PathVariable long id, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		
		servicioTableros.borrarTableroById(id);
		
		
		
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
		//model.addAttribute("tableros", servicioTableros.getTableros());
		model.addAttribute("tableros", servicioTableros.getTableroByOwner(usuarioActual));
		return "main";
	}
		
	// GET PAGINA CREACION DE LISTAS
	
	@GetMapping("/crearLista")
	public String crearLista(Model model, HttpSession sesion) {
		
		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		List<Tablero> tableros = usuarioActual.getTableros();
		
		//model.addAttribute("tableros",tableros);
		model.addAttribute("tableros", servicioTableros.getTableroByOwner(usuarioActual));
		//model.addAttribute("tableros", servicioTableros.getTableros());
		
		return "GetLista";
	}
	
	
	// GET BORRAR LISTA
	
	@GetMapping("/borrarLista/{id}")
	public String listaDELETE(Model model, @PathVariable long id, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		
		servicioListas.borrarListaById(id);
		
		
		
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
		model.addAttribute("tableros", servicioTableros.getTableroByOwner(usuarioActual));
		//model.addAttribute("tableros", servicioTableros.getTableros());
		return "main";
	}
	
	
	// POST CREAR LISTA
	
	@PostMapping("/Lista")
	public String addLista(Model model, @RequestParam String nombre, @RequestParam String nombreTablero, HttpSession sesion) {
		/*	
		Optional<Usuario> opt = usuarioRepository.findById(userID);
		Usuario usu = opt.get();
			
		// Se crea la lista con ese nombre y se añade al tablero del usuario
		Lista list = new Lista(nombre);
		listaRepository.save(list);
		usu.getTablero(0).addLista(list);
		usuarioRepository.save(usu);
			
		model.addAttribute("usu", usu);
		model.addAttribute("tableros", usu.getTableros());
		*/
		
		
		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		Lista list = new Lista(nombre);
		//listaRepository.save(list);
		Tablero tab = servicioTableros.getTableroByNombre(nombreTablero);
		//long id = tab.getId();
		
		
		servicioTableros.getTableroById(tab.getId());
		tab.addLista(list);
		servicioListas.guardarLista(list);
		servicioTableros.guardarTablero(tab);
		//usuarioActual.getTableros();
		
		//usuarioActual.getTablero(tab.getId());
		
		//usuarioActual.getTablero(id).addLista(list);
		//model.addAttribute("usuarioActual", usuarioActual);
		
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
		model.addAttribute("tableros", servicioTableros.getTableroByOwner(usuarioActual));
		//model.addAttribute("tableros", usuarioActual.getTableros());
		//model.addAttribute("tableros", servicioTableros.getTableros());

		return "main";
	}
	
	
	//GET MODIFICAR TARJETA
	
	
	@GetMapping("/modLista")
	public String listaMOD(Model model, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
			
			
		model.addAttribute("listas", servicioListas.getListas());
		return "ModLista";
	}
	
	
	// POST MODIFICAR LISTAS
	
	@PostMapping("/modLista")
	public String modLista(Model model, @RequestParam String nombre, HttpSession sesion) {
		/*	
		Optional<Usuario> opt = usuarioRepository.findById(userID);
		Usuario usu = opt.get();
				
		// Se crea la lista con ese nombre y se añade al tablero del usuario
		Lista list = new Lista(nombre);
		listaRepository.save(list);
		usu.getTablero(0).addLista(list);
		usuarioRepository.save(usu);
				
		model.addAttribute("usu", usu);
		model.addAttribute("tableros", usu.getTableros());
		*/
			
			
		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
			
		// QUEDA POR HACER
			

			
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
		model.addAttribute("tableros", servicioTableros.getTableroByOwner(usuarioActual));
		//model.addAttribute("tableros", usuarioActual.getTableros());
		//model.addAttribute("tableros", servicioTableros.getTableros());

		return "main";
	}
	
	
	// GET PAGINA CREAR TARJETA
	
	@GetMapping("/crearTarjeta")
	public String crearTarjeta(Model model, HttpSession sesion) {
		
		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		//List<Lista> listas = usuarioActual.getListas();
		
		//model.addAttribute("tableros",tableros);
		
		model.addAttribute("listas", servicioListas.getListas());
		
		return "GetTarjeta";
	}
	
	
	//GET BORRAR TARJETA
	
	
	@GetMapping("/borrarTarjeta/{id}")
	public String tarjetaDELETE(Model model, @PathVariable long id, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
			
		servicioTarjetas.borrarTarjetaById(id);
			
			
			
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
		//model.addAttribute("tableros", servicioTableros.getTableros());
		model.addAttribute("tableros", servicioTableros.getTableroByOwner(usuarioActual));
		return "main";
	}
	
	
	//GET MODIFICAR TARJETA
	
	
	@GetMapping("/modTarjeta")
	public String tarjetaMOD(Model model, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		
		
		model.addAttribute("tarjetas", servicioTarjetas.getTarjetas());
		return "ModTarjeta";
	}
	
	
	// POST MODIFICAR TARJETAS
	
	@PostMapping("/modTarjeta")
	public String modTarjeta(Model model, @RequestParam String nombre, @RequestParam(defaultValue="") String fechaFin,
			@RequestParam(defaultValue="") String descripcion, @RequestParam String listaAsociada, @RequestParam String listTarjetas, HttpSession sesion) {
		/*	
		Optional<Usuario> opt = usuarioRepository.findById(userID);
		Usuario usu = opt.get();
			
		// Se crea la lista con ese nombre y se añade al tablero del usuario
		Lista list = new Lista(nombre);
		listaRepository.save(list);
		usu.getTablero(0).addLista(list);
		usuarioRepository.save(usu);
			
		model.addAttribute("usu", usu);
		model.addAttribute("tableros", usu.getTableros());
		*/
		
		
		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		
		servicioTarjetas.borrarTarjetaByNombre(listTarjetas);
		
		//Lista list = servicioListas.getListaByNombre(listaAsociada);
		//Tarjeta tarjeta = new Tarjeta(nombre,fechaFin,descripcion,list);
		
		// QUEDA POR HACER
		
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
		
		//model.addAttribute("tableros", usuarioActual.getTableros());
		//model.addAttribute("tableros", servicioTableros.getTableros());
		model.addAttribute("tableros", servicioTableros.getTableroByOwner(usuarioActual));
		return "main";
	}
		
	
	// POST CREAR TARJETA
	
	@PostMapping("/Tarjeta")
	public String addTarjeta(Model model, @RequestParam String nombre, @RequestParam(defaultValue="") String fechaFin,
			@RequestParam(defaultValue="") String descripcion, @RequestParam String listaAsociada, HttpSession sesion) {

		/*Optional<Usuario> optu = usuarioRepository.findById(userID);
		Usuario usu = optu.get();
		
		Optional<Lista> opt = listaRepository.findByNombre(listaAsociada);
		Lista list;
		if (opt.isPresent()) {
			// Si existe, se usa esa lista.
			list = opt.get();
		} 
		else {
			// Si no existe, se crea la lista
			list = new Lista(listaAsociada);
			listaRepository.save(list);
			usu.getTablero(0).addLista(list);
		}
		Tarjeta tarjeta = new Tarjeta(nombre,fechaFin,descripcion, list);
		list.addTarjeta(tarjeta);
		tarjetaRepository.save(tarjeta);
		listaRepository.save(list);
		
		model.addAttribute("usu", usu);
		model.addAttribute("tableros", usu.getTableros());*/
		
		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		
		//Lista list = new Lista(listaAsociada);
		//listaRepository.save(list);
		Lista list = servicioListas.getListaByNombre(listaAsociada);
		Tarjeta tarjeta = new Tarjeta(nombre,fechaFin,descripcion,list);
		
		//long id = tab.getId();
		
		servicioListas.getListaById(list.getId());
		list.addTarjeta(tarjeta);
		
		servicioTarjetas.guardarTarjeta(tarjeta);
		servicioListas.guardarLista(list);
		/*
		servicioTableros.getTableroById(tab.getId());
		tab.addLista(list);
		servicioListas.guardarLista(list);
		servicioTableros.guardarTablero(tab);
		*/
		
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
		//model.addAttribute("tableros", servicioTableros.getTableros());
		model.addAttribute("tableros", servicioTableros.getTableroByOwner(usuarioActual));
		return "main";
	}
	
	
	
	// PRUEBA CHECKLISTS
	
	@GetMapping("/consultarChecklist")
	public String checklist(Model model) {
		return "checklist";
	}
	
	
	
	
	
	
	
	/*
	@GetMapping("/nuevoUsuario")
	public String registrarse1(Model model)
	{
		
		return "registrarse";
	}
	*/
	
	
	
	
	
	
	
	
	/*@PostMapping("/nuevoUsuario")
	public String registrarse(Model model, @RequestParam String nombreUsuario,
			@RequestParam String email, @RequestParam String contrasenya) {

		Usuario usuario = new Usuario(nombreUsuario, email, contrasenya);
		usuarioRepository.save(usuario);
//		usuarioActual = usuario;
		userID = usuario.getId();
		
		model.addAttribute("usu", usuario);
			
		Tablero t = new Tablero("Tablero de "+usuario.getNombreUsuario(), usuario);
		usuario.addTablero(t);
		tableroRepository.save(t);
		model.addAttribute("tableros", usuario.getTableros());
		return "main";
	}*/
/*
	@GetMapping("/iniciarSesion")
	public String iniciarSesion()
	{
		return "iniciarSesion";
	}
	*/
	/*@PostMapping("/usuarioAcceso")
	public String iniciarSesion(Model model, Usuario usuario, HttpSession sesion)
	{
		Optional<Usuario> opt = usuarioRepository.findByNombreUsuarioAndContrasenyaAndEmail(usuario.getNombreUsuario(), usuario.getContrasenya(), usuario.getEmail());
		if(opt.isPresent()) {
			Usuario usu = opt.get();
//			usuarioActual = usu;
			userID = usu.getId();
			model.addAttribute("usu", usu);
			model.addAttribute("tableros", usu.getTableros());
			sesion.setAttribute("usuarioActual", usuario);
			return "main";
		}
		else {
			return "usuarioNoExiste";
		}

	}*/
	
	
	/*
	@GetMapping("/perfil")
	public String userProfileView(Model model, HttpSession sesion) {*/
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

	
		// Es imprescindible buscarlo en el repositorio para comprobar que existe y cargar los datos actualizados
		/*Optional<Usuario> opt = usuarioRepository.findById(userID);
		Usuario usu = opt.get();
		
		
		model.addAttribute("nombre", usu.getNombreUsuario());
		return "miPerfil";
	}*/
	/*
	@GetMapping("/crearTablero")
	public String verTarjeta(Model model) {
		return "GetTablero";
	}*/
/*
	@PostMapping("/Tablero")
	public String addTablero(Model model, @RequestParam String nombre,
			@RequestParam(required=false, defaultValue="") String fechaFin , @RequestParam(required=false, defaultValue="") String descripcion) {
		
		Optional<Usuario> opt = usuarioRepository.findById(userID);
		Usuario usu = opt.get();
*/
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
		/*
		Tablero tablero = new Tablero(nombre);
		
		usu.addTablero(tablero);
		tableroRepository.save(tablero);

		return "main";
	}*/
	
	// GET PAGINA CREACION DE LISTAS
	/*
	@GetMapping("/crearLista")
	public String crearLista(Model model) {
		return "GetLista";
	}
*/
	
	/*
	@PostMapping("/Lista")
	public String addLista(Model model, @RequestParam String nombre) {
		
		Optional<Usuario> opt = usuarioRepository.findById(userID);
		Usuario usu = opt.get();
		
		// Se crea la lista con ese nombre y se añade al tablero del usuario
		Lista list = new Lista(nombre);
		listaRepository.save(list);
		usu.getTablero(0).addLista(list);
		usuarioRepository.save(usu);
		
		model.addAttribute("usu", usu);
		model.addAttribute("tableros", usu.getTableros());

		return "main";
	}
	*/
	/*
	@GetMapping("/crearTarjeta")
	public String crearTarjeta(Model model) {
		
		return "GetTarjeta";
	}
	
	@GetMapping("/borrar/tarjeta/{id}")
	public String borrarTarjeta(Model model, @PathVariable long id) {
		
		Optional<Tarjeta> optj = tarjetaRepository.findById(id);
		if(optj.isPresent()) {
			Tarjeta tarj = optj.get();
			Lista l = tarj.getListaAsociada();
			l.removeTarjeta(tarj);
			listaRepository.save(l);
			tarjetaRepository.delete(tarj);
		}
		Optional<Usuario> optu = usuarioRepository.findById(userID);
		Usuario usu = optu.get();
		model.addAttribute("usu", usu);
		model.addAttribute("tableros", usu.getTableros());
		return "main";
	}
	
	
	@PostMapping("/Tarjeta")
	public String addTarjeta(Model model, @RequestParam String nombre, @RequestParam(defaultValue="") String fechaFin,
			@RequestParam(defaultValue="") String descripcion, @RequestParam String listaAsociada) {

		Optional<Usuario> optu = usuarioRepository.findById(userID);
		Usuario usu = optu.get();
		
		Optional<Lista> opt = listaRepository.findByNombre(listaAsociada);
		Lista list;
		if (opt.isPresent()) {
			// Si existe, se usa esa lista.
			list = opt.get();
		} 
		else {
			// Si no existe, se crea la lista
			list = new Lista(listaAsociada);
			listaRepository.save(list);
			usu.getTablero(0).addLista(list);
		}
		Tarjeta tarjeta = new Tarjeta(nombre,fechaFin,descripcion, list);
		list.addTarjeta(tarjeta);
		tarjetaRepository.save(tarjeta);
		listaRepository.save(list);
		
		model.addAttribute("usu", usu);
		model.addAttribute("tableros", usu.getTableros());

		return "main";
	}*/

}
