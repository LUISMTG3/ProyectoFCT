package com.springproyectofct.controladores;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springproyectofct.DAO.UsuarioDAO;
import com.springproyectofct.DAO.UsuarioDaoImpl;
import com.springproyectofct.modelo.Comentario;
import com.springproyectofct.modelo.Contacto;
import com.springproyectofct.modelo.Invitacion;
import com.springproyectofct.modelo.Publicacion;
import com.springproyectofct.modelo.Usuario;
import com.springproyectofct.seguridad.AccesoValido;
import com.springproyectofct.seguridad.UsuarioValido;
import com.springproyectofct.util.ContactoUtil;
import com.springproyectofct.util.ImagenUtil;
import com.springproyectofct.util.InvitacionUtil;
import com.springproyectofct.util.Jpautil;
import com.springproyectofct.util.PublicacionUtil;
import com.springproyectofct.util.usuarioRepetidoUtil;

@Controller
public class UsuarioController implements ErrorController{

	private UsuarioDAO usuario = new UsuarioDaoImpl();
	private Usuario usuarioLogeado = new Usuario();
	private UsuarioValido usuarioValido = new UsuarioValido();
	private AccesoValido AccesValid = new AccesoValido();
	private usuarioRepetidoUtil usuRepUtil = new usuarioRepetidoUtil();
	ImagenUtil imgUtil = new ImagenUtil();
	InvitacionUtil invitacionUtil = new InvitacionUtil();
	PublicacionUtil publicUtil = new PublicacionUtil();
	ContactoUtil contactoUtil = new ContactoUtil();
	int loginCorrecto = 1;
	boolean usuarioRepetido = false;
	int numeroPublicaciones = 0;
	int ultimaPublicacion = 9;
	MultipartFile imagen;
//	String ruta = "C:\\Users\\willt\\eclipse-workspace2\\ProyectoFCTSpring\\src\\main\\resources\\static";
	String ruta = "C:\\Users\\willt\\eclipse-workspace2\\ProyectoFCTSpring\\target\\classes\\static";
	String fomartoFecha = "dd.MM'T'HH.mm";
	private ErrorAttributes errorAttributes;
	private final static String ERROR_PATH = "/error";

	
	
	
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

			List<Usuario> usuarios = contactoUtil.traerNoContactos(usuarioLogeado);
						
			List<Usuario> usuariosContactados = contactoUtil.traerContactos(usuarioLogeado);

			usuarios.remove(usuarioLogeado);
			
			model.addAttribute("listaUsuarios", usuarios);
			
			model.addAttribute("listaContactos", usuariosContactados);

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
	
	
	@RequestMapping(value = "/eliminarContacto/{id}")
	public String eliminarContacto(@ModelAttribute(name = "id") int id) {

		if (AccesValid.AccesoValido(usuarioLogeado)) {

			Usuario usuarioEliminado = usuario.findOne(id);	
			
			Contacto contacto = contactoUtil.buscarContacto(usuarioLogeado, usuarioEliminado);
			
			System.out.println(contacto.getId());
			System.out.println(contacto.getUsuario1().getNombre());
			System.out.println(contacto.getUsuario2().getNombre());
			
			usuarioLogeado.eliminarContacto(contacto);

			usuarioEliminado.eliminarContacto(contacto);
			
			usuario.update(usuarioEliminado);

			usuario.update(usuarioLogeado);

			return "redirect:/usuario/contactos";

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

			usuarioLogeado = usuario.findOne(loginCorrecto);
			
			usuario.update(usuarioLogeado);
			
			List<Publicacion> publicaciones = new ArrayList<Publicacion>();

			List<Usuario> usuarios = new ArrayList<Usuario>();

			List<String> avatares = new ArrayList<String>();

			for (Contacto contacto : usuarioLogeado.getContactos()) {

				usuarios.add(contacto.getUsuario2());
				publicaciones.addAll(contacto.getUsuario2().getPublicaciones());
				avatares.add(contacto.getUsuario2().getAvatar());

			}

			publicaciones.addAll(usuarioLogeado.getPublicaciones());

			Collections.sort(publicaciones, new Publicacion());

			if ((ultimaPublicacion += numeroPublicaciones) < (publicaciones.size() - 1)) {
				publicaciones.subList(ultimaPublicacion, publicaciones.size()).clear();
			}

			model.addAttribute("listaPublicaciones", publicaciones);

			model.addAttribute("listaContactos", usuarios);

			model.addAttribute("numeroPublicaciones", numeroPublicaciones);

			model.addAttribute("publicacion", new Publicacion());

			model.addAttribute("comentario");

			model.addAttribute("idPublicacion");

			return "home";

		}

		return "redirect:/";

	}

	@RequestMapping(value = "/home/{numeroPublicaciones}")
	public String verMas(@PathVariable(name = "numeroPublicaciones", required = true) int numeroPublicaciones) {

		this.numeroPublicaciones += numeroPublicaciones;

		return "redirect:/home";

	}

	@PostMapping(value = "/home/new/coment/{idPublicacion}")
	public String añadirComentario(@PathVariable(name = "idPublicacion" , required = true) int idPublicacion,
			@RequestParam(name = "comentario", required = false) String comentario) throws InterruptedException {

		if (AccesValid.AccesoValido(usuarioLogeado)) {
			
			
			Comentario comentarioNuevo = new Comentario();
			comentarioNuevo.setTexto(comentario);
			comentarioNuevo.setFecha(LocalDateTime.now());
			comentarioNuevo.setUsuarioCreador(usuarioLogeado);

			Publicacion publicacion = new Publicacion();

			for (Usuario usuario2 : usuario.findAll()) {

				for (Publicacion publicacionIndex : usuario2.getPublicaciones()) {

					if (publicacionIndex.getId() == idPublicacion) {

						publicacion = publicacionIndex;

						System.out.println("           HOLAAAAAAA        ");
						
					}
				}
			}

//			Publicacion publicacion = publicUtil.traerPublicacion(idPublicacion);

			
			
			publicacion.añadirComentario(comentarioNuevo);

			System.out.println(idPublicacion + "\n \n " + comentarioNuevo.getUsuarioCreador());

			System.out.println(usuarioLogeado.getNombre() + "      Usuario logueado");

			System.out.println(publicacion.getUsuario().getNombre() + " \n ");

			usuario.update(publicacion.getUsuario());

			return "redirect:/home";

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
				publicacion.setTitulo(comentario);

				if (!multipartFile.isEmpty()) {
					publicacion
							.setImagen("\\imagenes\\"
									+ LocalDateTime.now()
											.format(DateTimeFormatter.ofPattern("yyyy_MM_dd'T'HH.mm.ss.SSS")).toString()
									+ multipartFile.getOriginalFilename());

					imgUtil.guardarImagen(ruta + publicacion.getImagen(), multipartFile);

				} else {
					publicacion.setImagen(null);
				}

				publicacion.setFecha(LocalDateTime.now());

				publicacion.setUsuario(usuarioLogeado);

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

			nuevoUsuario.setAvatar("\\imagenes\\"
					+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd'T'HH.mm.ss.SSS")).toString()
					+ multipartFile.getOriginalFilename());

			imgUtil.guardarImagen(ruta + nuevoUsuario.getAvatar(), multipartFile);

		}else {
			nuevoUsuario.setAvatar("\\imagenes\\user.png");
		}

		usuario.create(nuevoUsuario);

		return "redirect:/";
	}

	public void AppErrorController(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	@RequestMapping(value = ERROR_PATH, produces = "text/html")
	public ModelAndView errorHtml(HttpServletRequest request) {
		return new ModelAndView("redirect:/home");
	}

	@RequestMapping(value = ERROR_PATH)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
		HttpStatus status = getStatus(request);
		return new ResponseEntity<Map<String, Object>>(body, status);
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

	private boolean getTraceParameter(HttpServletRequest request) {
		String parameter = request.getParameter("trace");
		if (parameter == null) {
			return false;
		}
		return !"false".equals(parameter.toLowerCase());
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return this.errorAttributes.getErrorAttributes((WebRequest) requestAttributes, includeStackTrace);
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode != null) {
			try {
				return HttpStatus.valueOf(statusCode);
			} catch (Exception ex) {
			}
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

}
