package com.springproyectofct.seguridad;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.springproyectofct.DAO.UsuarioDAO;
import com.springproyectofct.DAO.UsuarioDaoImpl;
import com.springproyectofct.modelo.Usuario;

@Controller
public class UsuarioValido {

	private UsuarioDAO usuarioDAO = new UsuarioDaoImpl();

	
	public UsuarioValido() {
		
	}
	
	
	public int validarUsuario(Usuario usuario) {

		
		List<Usuario> usuarios = usuarioDAO.findAll();

	
		for(Usuario usuario2 : usuarios) {
			
			if(usuario2.equals(usuario)) {
				
				return (int) usuario2.getId();
			
			}
		}
		
		return 0;
		

	}

}
