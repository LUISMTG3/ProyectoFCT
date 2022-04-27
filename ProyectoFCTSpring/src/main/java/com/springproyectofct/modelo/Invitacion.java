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
	private Usuario usuarioSolicitud;

	
	private int idUsuarioReceptor;

	
	private int idUsuarioEmisor;

	
	public Invitacion() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuarioSolicitud() {
		return usuarioSolicitud;
	}

	public void setUsuarioSolicitud(Usuario usuarioSolicitud) {
		this.usuarioSolicitud = usuarioSolicitud;
	}

	public int getIdUsuarioReceptor() {
		return idUsuarioReceptor;
	}

	public void setIdUsuarioReceptor(int idUsuarioReceptor) {
		this.idUsuarioReceptor = idUsuarioReceptor;
	}

	public int getIdUsuarioEmisor() {
		return idUsuarioEmisor;
	}

	public void setIdUsuarioEmisor(int idUsuarioEmisor) {
		this.idUsuarioEmisor = idUsuarioEmisor;
	}

}
