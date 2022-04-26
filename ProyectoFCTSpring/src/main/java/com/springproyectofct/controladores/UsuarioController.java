package com.springproyectofct.controladores;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import com.springproyectofct.modelo.Publicacion;
import com.springproyectofct.modelo.Usuario;
import com.springproyectofct.seguridad.UsuarioValido;
import com.springproyectofct.util.ImagenUtil;

@Controller
public class UsuarioController {

	private UsuarioDAO usuario = new UsuarioDaoImpl();
	private Usuario usuarioLogeado = new Usuario();
	private UsuarioValido usuarioValido = new UsuarioValido();
	ImagenUtil imgUtil = new ImagenUtil();
	int loginCorrecto = 1;
	MultipartFile imagen;
	String ruta = "C:\\Users\\willt\\eclipse-workspace2\\ProyectoFCTSpring\\target\\classes\\static\\";
	String fomartoFecha = "yyyy_MM_dd'T'HH.mm.ss.SSS";
	
	
	
	@PostMapping("/validarUsuario")
	public String validarUsuario(@ModelAttribute("usuarioLogin") Usuario usuarioLogin, Model model) {

		loginCorrecto = 1;

		if ( (loginCorrecto = usuarioValido.validarUsuario(usuarioLogin)) != 0) {
			
			usuarioLogeado = usuario.findOne(loginCorrecto);
			
			return "redirect:/home";
		}

		return "redirect:/";

	}
 
	@GetMapping("/usuario/list")
	public String listado(Model model) {

		model.addAttribute("listaUsuarios", usuario.findAll());

		model.addAttribute("id");

		return "list";
	}
	
	@RequestMapping(value = "/solicitud/{id}")
	public String enviarSolicitud(@PathVariable(name = "id", required = false) int id) {


		usuarioLogeado =  usuario.findOne(loginCorrecto);
		
//		usuarioLogeado.añadirContacto();
		 
		usuario.update(usuarioLogeado);
		
		

		return "home";
		

	}
	
	
	
	@GetMapping("/home")
	public String home(Model model) throws IllegalStateException, IOException {
		
		model.addAttribute("listaPublicaciones", usuario.findOne(2).getPublicaciones());

		model.addAttribute("publicacion" , new Publicacion());
				
		return "home";
	}
	
	
	
	
	@PostMapping("/home/new")
	public String home2(@RequestParam(name = "imagen", required = false) MultipartFile multipartFile, @RequestParam(name = "comentario", required = false) String comentario ) throws IllegalStateException, IOException {

		
		if(!comentario.isEmpty()) {
			
			Publicacion publicacion = new Publicacion();
			publicacion.setComentario(comentario);
			
			if(multipartFile != null) {
				publicacion.setImagen( "imagenes\\"
						+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd'T'HH.mm.ss.SSS")).toString()
						+ multipartFile.getOriginalFilename());
				
				imgUtil.guardarImagen(ruta + publicacion.getImagen(), multipartFile);
				
				
			}
			
			publicacion.setFecha(LocalDateTime.now());
			
			usuarioLogeado =  usuario.findOne(loginCorrecto);
			
			usuarioLogeado.añadirPublicacion(publicacion);
			 
			usuario.update(usuarioLogeado);
			
			
		}
		
		return "redirect:/home";
	}
	
	
	
	@GetMapping("/")
	public String login(Model model) {

		model.addAttribute("usuarioLogin", new Usuario());
//
		model.addAttribute("loginCorrecto", loginCorrecto);

//		model.addAttribute("imagen");

		return "login";
	}
	
	
	
	

	@GetMapping("/usuario/new")
	public String nuevoUsuarioForm(Model model) {

		model.addAttribute("usuarioForm", new Usuario());

		return "form";
	}
	
	

	@PostMapping("/usuario/new/submit")
	public String nuevoUsuarioSubmit(@ModelAttribute("usuarioForm") Usuario nuevoUsuario) {

		usuario.create(nuevoUsuario);

		return "redirect:/usuario/list";
	}


}
