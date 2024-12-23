package com.classes;

import java.time.LocalDateTime;
import java.util.Random;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pelicula {

	@Id
	private long id;
	private String titulo;
	private LocalDateTime fecha;
	private String posterImagen;
	private String sinopsis;
	
	
	
	public Pelicula(String titulo, String posterImagen, String sinopsis) {
		Random r = new Random();
		
		
		this.setId(r.nextInt(1000));
		this.setTexto(titulo);
		this.setFecha(LocalDateTime.now());
		this.setImagen(posterImagen);
		this.sinopsis = sinopsis;
	}

	public Pelicula() {
		
	}


	public String getSinopsis() {
		return this.sinopsis;
	}
	public long getId() {
		return id;
	}
	

	
	public String getImagen() {
		return this.posterImagen;
	}
	
	public void setImagen(String imagen) {
		this.posterImagen = imagen;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getTitulo() {
		return this.titulo;
	}



	public void setTexto(String texto) {
		this.titulo = texto;
	}



	public LocalDateTime getFecha() {
		return fecha;
	}



	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
}
