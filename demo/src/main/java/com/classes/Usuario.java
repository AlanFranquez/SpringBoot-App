package com.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.id.factory.internal.AutoGenerationTypeStrategy;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PersistenceContext;

@Entity
public class Usuario {
	
	
	
	@jakarta.persistence.Id
	private long Id;
	
	private String username;
	
	private String password;
	
	 @OneToMany
	 private List<Pelicula> peliculas;  // Colección de películas del usuario

	
	public Usuario(String username, String password) {
		Random r = new Random();
		this.Id = r.nextInt(1000);
		this.username = username;
		this.password = password;
		this.peliculas = new ArrayList<Pelicula>();
	}
	
	public Usuario() {
		
	}

	public String getPassword() {
		return password;
	}
	
	public void addPelicula(Pelicula p) {
		this.peliculas.add(p);
	}
	
	public List<Pelicula> getMovies() {
		return this.peliculas;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public long getId() {
		return this.Id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
