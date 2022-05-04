package com.springproyectofct.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Invitacion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	private Usuario UsuarioSolicitud;

	private int IdUsuarioReceptor;

	private int IdUsuarioEmisor;

	private String NombreUsuarioEmisor;

	private boolean resuelta;

	public Invitacion() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreUsuarioEmisor() {
		return NombreUsuarioEmisor;
	}

	public void setNombreUsuarioEmisor(String nombreUsuarioEmisor) {
		NombreUsuarioEmisor = nombreUsuarioEmisor;
	}

	public Usuario getUsuarioSolicitud() {
		return UsuarioSolicitud;
	}

	public void setUsuarioSolicitud(Usuario usuarioSolicitud) {
		this.UsuarioSolicitud = usuarioSolicitud;
	}

	public int getIdUsuarioReceptor() {
		return IdUsuarioReceptor;
	}

	public void setIdUsuarioReceptor(int idUsuarioReceptor) {
		this.IdUsuarioReceptor = idUsuarioReceptor;
	}

	public int getIdUsuarioEmisor() {
		return IdUsuarioEmisor;
	}

	public void setIdUsuarioEmisor(int idUsuarioEmisor) {
		this.IdUsuarioEmisor = idUsuarioEmisor;
	}

	public boolean isResuelta() {
		return resuelta;
	}

	public void setResuelta(boolean resuelta) {
		this.resuelta = resuelta;
	}

}
