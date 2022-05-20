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
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Publicacion> publicaciones = new ArrayList<>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "usuario1", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Contacto> contactos = new ArrayList<>();

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "UsuarioSolicitud", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Invitacion> invitaciones = new ArrayList<>();
	
	@Email
	private String email;
	
	private String avatar;

	public Usuario() {
	}

	public Usuario(String nombre, String contraseña, String avatar) {
		super();
		this.nombre = nombre;
		this.password = contraseña;
		this.avatar = avatar;
	}
	
	public Usuario(String nombre, String contraseña) {
		super();
		this.nombre = nombre;
		this.password = contraseña;
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

	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

	public List<Invitacion> getInvitaciones() {
		return invitaciones;
	}

	public void setInvitaciones(List<Invitacion> invitaciones) {
		this.invitaciones = invitaciones;
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", telefono=" + "]";
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
