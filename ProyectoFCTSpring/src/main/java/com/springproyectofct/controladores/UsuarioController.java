package com.springproyectofct.controladores;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springproyectofct.DAO.UsuarioDAO;
import com.springproyectofct.DAO.UsuarioDaoImpl;
import com.springproyectofct.modelo.Contacto;
import com.springproyectofct.modelo.Invitacion;
import com.springproyectofct.modelo.Publicacion;
import com.springproyectofct.modelo.Usuario;
import com.springproyectofct.seguridad.AccesoValido;
import com.springproyectofct.seguridad.UsuarioValido;
import com.springproyectofct.util.ImagenUtil;
import com.springproyectofct.util.InvitacionUtil;
import com.springproyectofct.util.usuarioRepetidoUtil;

@Controller
public class UsuarioController {

	private UsuarioDAO usuario = new UsuarioDaoImpl();
	private Usuario usuarioLogeado = new Usuario();
	private UsuarioValido usuarioValido = new UsuarioValido();
	private AccesoValido AccesValid = new AccesoValido();
	private usuarioRepetidoUtil usuRepUtil = new usuarioRepetidoUtil();
	ImagenUtil imgUtil = new ImagenUtil();
	InvitacionUtil invitacionUtil = new InvitacionUtil();
	int loginCorrecto = 1;
	boolean usuarioRepetido = false;
	MultipartFile imagen;
	String ruta = "C:\\Users\\willt\\eclipse-workspace2\\ProyectoFCTSpring\\target\\classes\\static\\";
	String fomartoFecha = "dd.MM'T'HH.mm";

	@PostMapping("/validarUsuario")
	public String validarUsuario(@ModelAttribute("usuarioLogin") Usuario usuarioLogin, Model model) {

		loginCorrecto = 1;

		if ((loginCorrecto = usuarioValido.validarUsuario(usuarioLogin)) != 0) {

			usuarioLogeado = usuario.findOne(loginCorrecto);

			return "redirect:/home";
		}

		return "redirect:/";

	}

	@GetMapping("/usuario/contactos")
	public String listadoContactos(Model model) {

		if (AccesValid.AccesoValido(usuarioLogeado)) {

			List<Usuario> usuarios = new ArrayList<Usuario>();

			usuarios.addAll(usuario.findAll());

			model.addAttribute("listaUsuarios", usuarios);

			model.addAttribute("id");

			return "contactos";

		}

		return "redirect:/";
	}

	@RequestMapping(value = "/aceptarContacto/{id}")
	public String aceptarContacto(@ModelAttribute(name = "id") int id) {

		if (AccesValid.AccesoValido(usuarioLogeado)) {

			Invitacion invitacion = invitacionUtil.buscarInvitacion(id, usuarioLogeado);

			Usuario usuarioEmisor = usuario.findOne(invitacion.getIdUsuarioEmisor());

			usuarioLogeado.borrarInvitacion(invitacion);

			usuarioLogeado.añadirContacto(new Contacto(usuarioLogeado, usuarioEmisor));

			usuarioEmisor.añadirContacto(new Contacto(usuarioEmisor, usuarioLogeado));

			usuario.update(usuarioEmisor);

			usuario.update(usuarioLogeado);

			return "redirect:/usuario/invitaciones";

		}

		return "redirect:/";

	}

	@RequestMapping(value = "/rechazarContacto/{id}")
	public String rechazarContacto(@ModelAttribute(name = "id") int id) {

		if (AccesValid.AccesoValido(usuarioLogeado)) {

			Invitacion invitacion = invitacionUtil.buscarInvitacion(id, usuarioLogeado);

			Usuario usuarioEmisor = usuario.findOne(invitacion.getIdUsuarioEmisor());

			usuarioLogeado.borrarInvitacion(invitacion);

			usuario.update(usuarioEmisor);

			usuario.update(usuarioLogeado);

			return "redirect:/usuario/invitaciones";

		}

		return "redirect:/";

	}

	@GetMapping("/usuario/invitaciones")
	public String listadoInvitaciones(Model model) {

		if (AccesValid.AccesoValido(usuarioLogeado)) {

			model.addAttribute("listaInvitaciones", usuarioLogeado.getInvitaciones());

			model.addAttribute("invitacion", new Invitacion());

			return "invitaciones";

		}

		return "redirect:/";

	}

	@RequestMapping(value = "/solicitud/{id}")
	public String enviarSolicitud(@PathVariable(name = "id", required = false) int id) {

		if (AccesValid.AccesoValido(usuarioLogeado)) {

			if (invitacionUtil.comprobarDuplicidad(usuario.findOne(id), usuarioLogeado)) {

				return "redirect:/usuario/contactos";

			}

			Usuario usuarioReceptor = usuario.findOne(id);

			Invitacion invitacion = new Invitacion();

			invitacion.setIdUsuarioEmisor(usuarioLogeado.getId());

			invitacion.setNombreUsuarioEmisor(usuarioLogeado.getNombre());

			invitacion.setIdUsuarioReceptor(usuarioReceptor.getId());

			usuarioReceptor.añadirInvitacion(invitacion);

			usuario.update(usuarioReceptor);

			return "redirect:/home";

		}

		return "redirect:/";

	}

	@GetMapping("/home")
	public String home(Model model) throws IllegalStateException, IOException {

		if (AccesValid.AccesoValido(usuarioLogeado)) {

			List<Publicacion> publicaciones = new ArrayList<Publicacion>();

			List<Usuario> usuarios = new ArrayList<Usuario>();

			for (Contacto contacto : usuarioLogeado.getContactos()) {

				usuarios.add(contacto.getUsuario2());

				publicaciones.addAll(contacto.getUsuario2().getPublicaciones());

			}

			publicaciones.addAll(usuarioLogeado.getPublicaciones());

			Collections.sort(publicaciones, new Publicacion());

			model.addAttribute("listaPublicaciones", publicaciones);

			model.addAttribute("listaContactos", usuarios);

			model.addAttribute("publicacion", new Publicacion());

			return "home";

		}

		return "redirect:/";

	}

	@PostMapping("/home/new")
	public String home2(@RequestParam(name = "imagen", required = false) MultipartFile multipartFile,
			@RequestParam(name = "comentario", required = false) String comentario)
			throws IllegalStateException, IOException {

		if (AccesValid.AccesoValido(usuarioLogeado)) {

			if (!comentario.isEmpty()) {

				Publicacion publicacion = new Publicacion();
				publicacion.setComentario(comentario);

				if (multipartFile != null) {
					publicacion
							.setImagen("imagenes\\"
									+ LocalDateTime.now()
											.format(DateTimeFormatter.ofPattern("yyyy_MM_dd'T'HH.mm.ss.SSS")).toString()
									+ multipartFile.getOriginalFilename());

					imgUtil.guardarImagen(ruta + publicacion.getImagen(), multipartFile);

				}

				publicacion.setFecha(LocalDateTime.now());

				usuarioLogeado = usuario.findOne(loginCorrecto);

				usuarioLogeado.añadirPublicacion(publicacion);

				usuario.update(usuarioLogeado);

			}

			return "redirect:/home";

		}

		return "redirect:/";

	}

	@GetMapping("/")
	public String login(Model model) {

		model.addAttribute("usuarioLogin", new Usuario());

		model.addAttribute("loginCorrecto", loginCorrecto);

		return "login";
	}

	@GetMapping("/usuario/new")
	public String nuevoUsuarioForm(Model model) {

		model.addAttribute("usuarioForm", new Usuario());

		model.addAttribute("usuarioRepetido", usuarioRepetido);

		return "form";
	}

	@PostMapping("/usuario/new/submit")
	public String nuevoUsuarioSubmit(@RequestParam(name = "imagen", required = false) MultipartFile multipartFile,
			@ModelAttribute("usuarioForm") Usuario nuevoUsuario) throws IllegalStateException, IOException {

		usuarioRepetido = false;

		if ((usuarioRepetido = usuRepUtil.usuarioRepetido(nuevoUsuario))) {

			return "redirect:/usuario/new";
		}

		if (multipartFile != null) {

			nuevoUsuario.setAvatar("avatares\\"
					+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd'T'HH.mm.ss.SSS")).toString()
					+ multipartFile.getOriginalFilename());

			imgUtil.guardarImagen(ruta + nuevoUsuario.getAvatar(), multipartFile);

		}

		usuario.create(nuevoUsuario);

		return "redirect:/";
	}

}
