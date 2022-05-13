package com.springproyectofct.modelo;

import java.time.LocalDateTime;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.lang.NonNull;

@Entity
public class Publicacion implements Comparator<Publicacion> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NonNull
	@Column(length = 10000)
	private String comentario;

	private String Imagen;

	private LocalDateTime fecha;

	@ManyToOne
	private Usuario usuario;
	
//	el objeto comentario contendra: id, id de usuario que comenta, string con el comentario, y fecha
//	@LazyCollection(LazyCollectionOption.FALSE)
//	@OneToMany(mappedBy = "comentarios", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Comentario> comentarios = new ArrayList<>();

	public Publicacion() {

	}

	public Publicacion(Usuario usuario, String comentario, String imagen, LocalDateTime fecha) {
		super();
		this.usuario = usuario;
		this.comentario = comentario;
		this.Imagen = imagen;
		this.fecha = fecha;
	}

	public Publicacion(String comentario, String imagen, Usuario usuario) {
		super();
		this.comentario = comentario;
		this.Imagen = imagen;
		this.usuario = usuario;
	}

	public Publicacion(String comentario, Usuario usuario) {
		super();
		this.comentario = comentario;
		this.usuario = usuario;
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

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
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

}
