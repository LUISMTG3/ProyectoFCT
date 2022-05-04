package com.springproyectofct.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Contacto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private Usuario usuario1;
	
	@ManyToOne
	private Usuario usuario2;
	
	
	public Contacto() {
		
	}
	
	public Contacto(Usuario usuario1, Usuario usuario2) {
		this.usuario1 = usuario1;
		this.usuario2 = usuario2;
	}


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
	
	
	public Usuario getUsuario2() {
		return usuario2;
	}


	public void setUsuario2(Usuario usuario2) {
		this.usuario2 = usuario2;
	}


	
	

	
	
}
