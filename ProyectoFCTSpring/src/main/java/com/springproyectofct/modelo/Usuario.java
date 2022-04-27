package com.springproyectofct.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.lang.NonNull;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	
	@NonNull
	@Column(unique = true)
	private String nombre;

	private String password;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	private List<Publicacion> publicaciones = new ArrayList<>();
	
	@OneToMany(mappedBy = "usuario1", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Contacto> contactos = new ArrayList<>();

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "usuarioSolicitud", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Invitacion> invitaciones = new ArrayList<>();
	
	@Email
	private String email;

	private String telefono;

	public Usuario() {
	}

	public Usuario(String nombre, String contraseña) {
		this.nombre = nombre;
		this.password = contraseña;
	}

	public Usuario(String nombre, String email, String telefono) {
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Publicacion> getPublicaciones() {
		return publicaciones;
	}

	public void setPublicaciones(List<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}
	
	public void añadirContacto(Contacto contacto) {
		contactos.add(contacto);
		contacto.setUsuario1(this);
	}
	
	public void eliminarContacto(Contacto contacto) {
		contactos.remove(contacto);
		contacto.setUsuario1(null);
	}
	
	public void añadirPublicacion(Publicacion publicacion) {
		publicaciones.add(publicacion);
		publicacion.setUsuario(this);
	}
	
	public void borrarPublicacion(Publicacion publicacion) {
		publicaciones.remove(publicacion);
		publicacion.setUsuario(null);
	}
	
	public void añadirInvitacion(Invitacion invitacion) {
		invitaciones.add(invitacion);
		invitacion.setUsuarioSolicitud(this);
	}
	
	public void borrarInvitacion(Invitacion invitacion) {
		invitaciones.remove(invitacion);
		invitacion.setUsuarioSolicitud(null);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", telefono=" + telefono + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object obj) {

		Usuario usuario = (Usuario) obj;

		if (this.getNombre().equals(usuario.getNombre()) && this.getPassword().equals(usuario.getPassword())) {
			return true;
		}

		return false;
	}

}
