package com.springproyectofct.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Target;
import org.springframework.lang.NonNull;

@Entity
public class Publicacion implements Comparator<Publicacion> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NonNull
	@Column(length = 10000)
	private String titulo;

	private String Imagen;

	private LocalDateTime fecha;

	@ManyToOne(targetEntity = Usuario.class)
	private Usuario usuario;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true,targetEntity = Comentario.class)
	private List<Comentario> comentarios = new ArrayList<>();
	

	public Publicacion() {

	}

	public Publicacion(Usuario usuario, String comentario, String imagen, LocalDateTime fecha) {
		super();
		this.usuario = usuario;
		this.titulo = comentario;
		this.Imagen = imagen;
		this.fecha = fecha;
	}

	public Publicacion(String titulo, String imagen, Usuario usuario) {
		super();
		this.titulo = titulo;
		this.Imagen = imagen;
		this.usuario = usuario;
	}

	public Publicacion(String titulo, Usuario usuario) {
		super();
		this.titulo = titulo;
		this.usuario = usuario;
	}
	
	public void añadirComentario(Comentario comentario) {
		comentarios.add(comentario);
		comentario.setPublicacion(this);
	}
	
	public void eliminarComentario(Comentario comentario) {
		comentarios.remove(comentario);
		comentario.setPublicacion(null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public String getImagen() {
		return Imagen;
	}

	public void setImagen(String imagen) {
		Imagen = imagen;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	@Override
	public int compare(Publicacion p1, Publicacion p2) {

		if (p1.getFecha().isBefore(p2.getFecha())) {
			
			return 1;
			
		} else if (p2.getFecha().isBefore(p1.getFecha())) {

			return -1;
			
		}

		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {

		Publicacion publicacion = (Publicacion) obj;

		if (this.getId() == publicacion.getId() ) {
			return true;
		}

		return false;
	}

}
