package com.springproyectofct.util;

import com.springproyectofct.DAO.UsuarioDAO;
import com.springproyectofct.DAO.UsuarioDaoImpl;
import com.springproyectofct.modelo.Publicacion;
import com.springproyectofct.modelo.Usuario;

public class PublicacionUtil {
	
	private UsuarioDAO usuario = new UsuarioDaoImpl();

	
	public PublicacionUtil() {
		
	}
	
	public Publicacion traerPublicacion(int id) {
		
		Publicacion publicacion = new Publicacion();
		
		publicacion.setId(id);
		
		for(Usuario usuario2 : usuario.findAll()) {
			
			for(Publicacion publicacionIndex : usuario2.getPublicaciones()) {
				
				if(publicacionIndex.equals(publicacion)) {
					return publicacionIndex;
				}
			}
		}
		
		return publicacion;
		
	}
}
