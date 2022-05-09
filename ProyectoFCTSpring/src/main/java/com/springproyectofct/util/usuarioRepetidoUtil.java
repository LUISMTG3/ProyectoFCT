package com.springproyectofct.util;

import com.springproyectofct.DAO.UsuarioDAO;
import com.springproyectofct.DAO.UsuarioDaoImpl;
import com.springproyectofct.modelo.Usuario;

public class usuarioRepetidoUtil {

	private UsuarioDAO usuario = new UsuarioDaoImpl();
	
	
	public boolean usuarioRepetido(Usuario usuarioNuevo) {
		
		for(Usuario usuario1 : usuario.findAll()) {
		
		if(usuario1.getNombre().equals(usuarioNuevo.getNombre())) {
			return true;
		}
		
		}
		
		
		return false;
	}
	
	
	
}
