package com.springproyectofct.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Contacto {

	@Id
	private int id;
	
	@ManyToOne
	private Usuario usuario1;
	
	@ManyToOne
	private Usuario usuario2;
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Usuario getUsuario1() {
		return usuario1;
	}


	public void setUsuario1(Usuario usuario1) {
		this.usuario1 = usuario1;
	}


	
	

	
	
}
