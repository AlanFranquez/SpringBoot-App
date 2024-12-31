package com.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {
	
	
	
	@jakarta.persistence.Id
	private long Id;
	
	private String username;
	
	private String password;

	private String profileImage;
	
	 @OneToMany
	 private List<Pelicula> peliculas;  // Colección de películas del usuario

	@OneToMany(mappedBy="author")
	private List<Review> reviews;
	
	public Usuario(String username, String password, String image) {
		Random r = new Random();
		this.Id = r.nextInt(1000);
		this.username = username;
		this.password = password;
		this.peliculas = new ArrayList<Pelicula>();
		this.profileImage = image;
		this.reviews = new ArrayList<Review>();
	}
	
	public Usuario() {
		
	}

	public String getPassword() {
		return password;
	}

	public void addReview(Review r) {
		this.reviews.add(r);
	}

	public List<Review> getReview() {
		return this.reviews;
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

	public String getImage() {
		return this.profileImage;
	}

	public void setImage(String link) {
		this.profileImage = link;
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
