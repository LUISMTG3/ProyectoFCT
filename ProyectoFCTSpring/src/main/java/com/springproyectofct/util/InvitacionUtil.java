package com.springproyectofct.util;

import com.springproyectofct.DAO.UsuarioDAO;
import com.springproyectofct.DAO.UsuarioDaoImpl;
import com.springproyectofct.modelo.Invitacion;
import com.springproyectofct.modelo.Usuario;

public class InvitacionUtil {

	public Invitacion buscarInvitacion(int id, Usuario usuarioLogeado) {

		for (Invitacion invitacion : usuarioLogeado.getInvitaciones()) {

			if (invitacion.getId() == id) {
				return invitacion;
			}

		}

		return null;

	}

	public boolean comprobarDuplicidad(Usuario usuarioReceptor, Usuario usuarioEmisor) {

		for (Invitacion invitacion : usuarioReceptor.getInvitaciones()) {

			if (invitacion.getIdUsuarioEmisor() == usuarioEmisor.getId()) {
				return true;
			}

		}

		return false;
	}

}
