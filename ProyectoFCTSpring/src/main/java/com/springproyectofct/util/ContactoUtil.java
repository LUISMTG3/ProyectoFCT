package com.springproyectofct.util;

import java.util.ArrayList;
import java.util.List;

import com.springproyectofct.DAO.UsuarioDAO;
import com.springproyectofct.DAO.UsuarioDaoImpl;
import com.springproyectofct.modelo.Contacto;
import com.springproyectofct.modelo.Usuario;

public class ContactoUtil {

	UsuarioDAO usuarioDao = new UsuarioDaoImpl();

	public Contacto buscarContacto(Usuario usuario1, Usuario usuario2) {

		for (Usuario usuario : usuarioDao.findAll()) {
			for (Contacto ContactoBuscado : usuario.getContactos()) {

				if (ContactoBuscado.getUsuario1().getId() == usuario1.getId()
						&& ContactoBuscado.getUsuario2().getId() == usuario2.getId()) {
					return ContactoBuscado;
				}

			}
		}

		return null;
	}

	public List<Usuario> traerContactos(Usuario usuarioLogeado) {

		List<Usuario> usuarios = new ArrayList<Usuario>();

		for (Contacto ContactoBuscado : usuarioLogeado.getContactos()) {
			
			usuarios.add(ContactoBuscado.getUsuario2());

		}
		
		usuarios.remove(usuarioLogeado);

		return usuarios;

	}

	public List<Usuario> traerNoContactos(Usuario usuarioLogeado) {

		List<Usuario> usuariosContactados = traerContactos(usuarioLogeado);
		
		List<Usuario> usuariosDevueltos = usuarioDao.findAll();
		
		
		usuariosDevueltos.removeAll(usuariosContactados);
		
		return usuariosDevueltos;

	}

}
