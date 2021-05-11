package com.example;

import org.apache.catalina.connector.Response;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.lista.Lista;
import com.example.lista.ListaRepository;
import com.example.tablero.Tablero;
import com.example.tablero.TableroRepository;
import com.example.tarjeta.Tarjeta;
import com.example.tarjeta.TarjetaRepository;
import com.example.usuario.Usuario;
import com.example.usuario.UsuarioRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

	

	// PAGINA DE INICIO

	@GetMapping("/")
	public String main() {
		return "pagSesion";
	}

	@GetMapping("/ayuda")
	public String ayudaUsuario() {
		return "ayuda";
	}

	// GET LOGIN REGISTRO USUARIO

	@GetMapping("/registrarse")
	public String registrarse() {
		return "registrarse";
	}

	// POST USUARIO NUEVO

	@PostMapping("/nuevoUsuario")
	public String registrarse(Model model, Usuario usuario, HttpSession sesion) {
		if (usuario.getNombreUsuario().trim().equals("") || usuario.getContrasenya().trim().equals("")
				|| usuario.getEmail().trim().equals("")) {

			return "error";
		} else {
			if (!servicioUsuarios.existeUsuario(usuario)) {
				servicioUsuarios.guardarUsuario(usuario);
				model.addAttribute("usu", usuario);
				sesion.setAttribute("usuarioActual", usuario);
				return "main";
			} else {

				return "error";
			}
		}
	}

	// GET LOGIN ACCESO

	@GetMapping("/iniciarSesion")
	public String iniciarSesion() {
		return "iniciarSesion";
	}

	// POST USUARIO ACCESO

	@PostMapping("/usuarioAcceso")
	public String iniciarSesion(Model model, @RequestParam String id, @RequestParam String pass, HttpSession sesion) {
		if (id.equals("") || pass.equals("")) {
			return "iniciarSesion";
		} else {
			for (Usuario usu : usuarioRepository.findAll()) {
				if ((usu.getNombreUsuario().equals(id) || usu.getEmail().equals(id))
						&& usu.getContrasenya().equals(pass)) {
					sesion.setAttribute("usuarioActual", usu);
					Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
					model.addAttribute("usu", usuarioActual);
					model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
					model.addAttribute("tableros", usuarioActual.getTableros());			
					if (usuarioActual.getTableros().size() > 0) {
						model.addAttribute("tablero_default", usuarioActual.getTableros().get(0));
					} else {

						model.addAttribute("tablero_default", usuarioActual.getTableros());
					}
					return "main";
				}
			}

		}
		return "iniciarSesion";
	}
	// PAGINA PRINCIPAL

	@GetMapping("/CambiarColor/verde/{id}")
	public String colorVerde(Model model, HttpSession sesion, @PathVariable long id) {
		
		
		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
	
		Tarjeta t = servicioTarjetas.getTarjetaById(id);
		t.setColor("verde");
		servicioTarjetas.guardarTarjeta(t);

		Lista l = t.getListaAsociada();
		for (Tablero tab : usuarioActual.getTableros()) {
			if (tab.getLista(l) != null)
				return "redirect:/pagPrincipal/" + usuarioActual.getTablero(tab).getId();
		}
		return "redirect:/pagPrincipal/";
	}
	
	@GetMapping("/CambiarColor/naranja/{id}")
	public String colorNaranja(Model model, HttpSession sesion, @PathVariable long id) {
		
		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
	
		Tarjeta t = servicioTarjetas.getTarjetaById(id);
		t.setColor("naranja");
		servicioTarjetas.guardarTarjeta(t);

		Lista l = t.getListaAsociada();
		for (Tablero tab : usuarioActual.getTableros()) {
			if (tab.getLista(l) != null)
				return "redirect:/pagPrincipal/" + usuarioActual.getTablero(tab).getId();
		}
		return "redirect:/pagPrincipal/";
	}
	
	@GetMapping("/CambiarColor/azul/{id}")
	public String colorAzul(Model model, HttpSession sesion, @PathVariable long id) {
		
		
		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
	
		Tarjeta t = servicioTarjetas.getTarjetaById(id);
		t.setColor("azul");
		servicioTarjetas.guardarTarjeta(t);

		Lista l = t.getListaAsociada();
		for (Tablero tab : usuarioActual.getTableros()) {
			if (tab.getLista(l) != null)
				return "redirect:/pagPrincipal/" + usuarioActual.getTablero(tab).getId();
		}
		return "redirect:/pagPrincipal/";
	}
	
	
	@GetMapping("/pagPrincipal/{id}")
	public String paginaInicialConID(Model model, HttpSession sesion, @PathVariable long id) {
		
		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
		model.addAttribute("tableros", usuarioActual.getTableros());
		//model.addAttribute("color", color);
		Optional<Tablero> t = tableroRepository.findById(id);

		if (t != null) {
			model.addAttribute("tablero_default", t.get());

		} else if (usuarioActual.getTableros().size() > 0) {
			model.addAttribute("tablero_default", usuarioActual.getTableros().get(0));

		} else {
			model.addAttribute("tablero_default", usuarioActual.getTableros());

		}

		return "main";
	}
	
	
	

	@GetMapping("/pagPrincipal")
	public String paginaInicial(Model model, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		model.addAttribute("usu", usuarioActual);
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
		model.addAttribute("tableros", usuarioActual.getTableros());
		

		return "redirect:/pagPrincipal/" + usuarioActual.getTablero(0).getId();
	}

	// PERFIL DEL USUARIO

	@GetMapping("/perfil")
	public String userProfileView(Model model, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());

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
			@RequestParam(required = false, defaultValue = "") String fechaFin,
			@RequestParam(required = false, defaultValue = "") String descripcion, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");

		Tablero tablero = new Tablero(nombre, usuarioActual);
		usuarioActual.addTablero(tablero);
		servicioTableros.guardarTablero(tablero);

		return "redirect:/pagPrincipal/" + tablero.getId();
		/*
		 * model.addAttribute("usu", usuarioActual); model.addAttribute("nombreUsuario",
		 * usuarioActual.getNombreUsuario()); model.addAttribute("tableros",
		 * servicioTableros.getTableroByOwner(usuarioActual));
		 * 
		 * return "main";
		 */
	}

	@GetMapping("/editarListas/{{id}}")
	public String editarListas(Model model, @PathVariable long id, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");

		servicioTableros.borrarTableroById(id);

		model.addAttribute("usu", usuarioActual);
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
		model.addAttribute("tableros", usuarioActual.getTableros());

		return "main";
	}

	@GetMapping("/borrarTablero/{id}")
	public String borrarTablero(Model model, @PathVariable long id, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		if (usuarioActual.getTableros().size() > 1) {
			Tablero tab = tableroRepository.getOne(id);

			for (Lista l : tab.getListas()) {
				tab.removeLista(l);

			}
			usuarioActual.removeTableroById(id);
			
			servicioTableros.borrarTableroById(id);

			for (Tablero t : usuarioActual.getTableros()) {
				if (t != null) {
					return "redirect:/pagPrincipal/" + t.getId();
				}
			}
			return null; // nunca debería llegar, asi que aqui vale cualquier cosa
		} else {
			return "redirect:/pagPrincipal/" + id;
		}

	}

	// GET PAGINA CREACION DE LISTAS

	@GetMapping("/crearLista")
	public String crearLista(Model model, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");
		List<Tablero> tableros = usuarioActual.getTableros();

		model.addAttribute("tableros", tableros);

		return "GetLista";
	}

	// GET BORRAR LISTA

	@GetMapping("/borrarLista/{id}")
	public String listaDELETE(Model model, @PathVariable long id, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");

		Tablero t = servicioTableros.getTableroByOwner(usuarioActual);
		Lista l = servicioListas.getListaById(id);
		t.removeLista(l);

		servicioListas.borrarListaById(id);

		return "redirect:/pagPrincipal/" + t.getId();

	}

	// POST CREAR LISTA

	@PostMapping("/Lista")
	public String addLista(Model model, @RequestParam String nombre, @RequestParam String nombreTablero,
			HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");

		Lista list = new Lista(nombre);
		Tablero tab = servicioTableros.getTableroByNombre(nombreTablero);

		servicioTableros.getTableroById(tab.getId());
		tab.addLista(list);
		servicioListas.guardarLista(list);
		servicioTableros.guardarTablero(tab);

		return "redirect:/pagPrincipal/" + tab.getId();

	}

	// GET MODIFICAR TARJETA

	@GetMapping("/modLista")
	public String listaMOD(Model model, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");

		model.addAttribute("listas", servicioListas.getListas());

		return "ModLista";
	}

	// POST MODIFICAR LISTAS

	@PostMapping("/modLista")
	public String modLista(Model model, @RequestParam String nombre, @RequestParam String listaAsociada,
			HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");

		Lista l = servicioListas.getListaByNombre(listaAsociada);
		l.setNombre(nombre);
		servicioListas.guardarLista(l);

		for (Tablero tab : usuarioActual.getTableros()) {
			if (tab.getLista(l) != null)
				return "redirect:/pagPrincipal/" + usuarioActual.getTablero(tab).getId();
		}
		// SI POR ALGUN MOTIVO QUE DESCONOZCO NO FUNCIONA, PUES TE MANDA CON UN TABLERO
		// 0
		return "redirect:/pagPrincipal/" + usuarioActual.getTablero(0).getId();
	}

	// GET PAGINA CREAR TARJETA

	@GetMapping("/crearTarjeta")
	public String crearTarjeta(Model model, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");

		model.addAttribute("listas", servicioListas.getListas());

		return "GetTarjeta";
	}

	// GET BORRAR TARJETA

	@GetMapping("/borrarTarjeta/{id}")
	public String tarjetaDELETE(Model model, @PathVariable long id, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");

		Tarjeta t = servicioTarjetas.getTarjetaById(id);
		Lista l = t.getListaAsociada();

		servicioTarjetas.borrarTarjetaById(id);

		/*
		 * model.addAttribute("usu", usuarioActual); model.addAttribute("nombreUsuario",
		 * usuarioActual.getNombreUsuario()); model.addAttribute("tableros",
		 * servicioTableros.getTableroByOwner(usuarioActual));
		 */

		for (Tablero tab : usuarioActual.getTableros()) {
			if (tab.getLista(l) != null)
				return "redirect:/pagPrincipal/" + usuarioActual.getTablero(tab).getId();
		}

		return "redirect:/pagPrincipal/" + usuarioActual.getTablero(0).getId();
	}

	// GET MODIFICAR TARJETA

	@GetMapping("/modTarjeta")
	public String tarjetaMOD(Model model, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");

		model.addAttribute("tarjetas", servicioTarjetas.getTarjetas());
		return "ModTarjeta";
	}

	// POST MODIFICAR TARJETAS

	@PostMapping("/modTarjeta")
	public String modTarjeta(Model model, @RequestParam String nombre, @RequestParam(defaultValue = "") String fechaFin,
			@RequestParam(defaultValue = "") String descripcion, @RequestParam String tarjetaAsociada,
			HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");

		Tarjeta t = servicioTarjetas.getTarjetaByNombre(tarjetaAsociada);
		t.setNombre(nombre);
		t.setFechaFin(fechaFin);
		t.setDescripcion(descripcion);
		servicioTarjetas.guardarTarjeta(t);

		Lista l = t.getListaAsociada();
		/*
		 * model.addAttribute("usu", usuarioActual); model.addAttribute("nombreUsuario",
		 * usuarioActual.getNombreUsuario()); model.addAttribute("tableros",
		 * servicioTableros.getTableroByOwner(usuarioActual));
		 */

		for (Tablero tab : usuarioActual.getTableros()) {
			if (tab.getLista(l) != null)
				return "redirect:/pagPrincipal/" + usuarioActual.getTablero(tab).getId();
		}

		return "redirect:/pagPrincipal/" + usuarioActual.getTablero(0).getId();
	}

	// POST CREAR TARJETA

	@PostMapping("/Tarjeta")
	public String addTarjeta(Model model, @RequestParam String nombre, @RequestParam(defaultValue = "") String fechaFin,
			@RequestParam(defaultValue = "") String descripcion, @RequestParam String listaAsociada,
			HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");

		Lista list = servicioListas.getListaByNombre(listaAsociada);
		Tarjeta tarjeta = new Tarjeta(nombre, fechaFin, descripcion, list);
		servicioListas.getListaById(list.getId());
		list.addTarjeta(tarjeta);
		servicioTarjetas.guardarTarjeta(tarjeta);
		servicioListas.guardarLista(list);

		return "redirect:/pagPrincipal";

	}

	// PRUEBA CHECKLISTS

	@GetMapping("/consultarChecklist")
	public String checklist(Model model) {
		return "checklist";
	}

	// GET TEMPLATE MOVER TARJETAS

	@GetMapping("/moverTarjetas")
	public String moverTarjetas(Model model, HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");

		model.addAttribute("listas", servicioListas.getListas());
		model.addAttribute("tarjetas", servicioTarjetas.getTarjetas());

		return "moverTarjetas";
	}

	// POST MOVER TARJETAS

	@PostMapping("/moverTarjetas")
	public String actTarjeta(Model model, @RequestParam String listaAsociada, @RequestParam String tarjetaAsociada,
			HttpSession sesion) {

		Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioActual");

		Lista list = servicioListas.getListaByNombre(listaAsociada);
		Tarjeta t = servicioTarjetas.getTarjetaByNombre(tarjetaAsociada);
		t.setListaAsociada(list);
		list.addTarjeta(t);
		servicioTarjetas.guardarTarjeta(t);
		servicioListas.guardarLista(list);
		/*
		 * model.addAttribute("usu", usuarioActual); model.addAttribute("nombreUsuario",
		 * usuarioActual.getNombreUsuario()); model.addAttribute("tableros",
		 * servicioTableros.getTableroByOwner(usuarioActual));
		 */

		for (Tablero tab : usuarioActual.getTableros()) {
			if (tab.getLista(list) != null)
				return "redirect:/pagPrincipal/" + usuarioActual.getTablero(tab).getId();
		}

		return "redirect:/pagPrincipal/" + usuarioActual.getTablero(0).getId();
	}

}
