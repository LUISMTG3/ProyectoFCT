package com.springproyectofct.seguridad;

import com.springproyectofct.modelo.Usuario;

public class AccesoValido {

	private UsuarioValido usuarioValido = new UsuarioValido();

	public AccesoValido() {
		
	}


	public boolean AccesoValido(Usuario usuario) {

		if ((usuarioValido.validarUsuario(usuario)) != 0) {

			return true;

		}
		
		return false;

	}

}
