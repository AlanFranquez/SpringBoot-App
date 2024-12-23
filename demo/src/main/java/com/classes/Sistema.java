package com.classes;

import java.util.ArrayList;
import java.util.List;

public class Sistema {

	private static Sistema instancia = null;
	private List<Usuario> usuarios;
	private List<Pelicula> peliculas;
	
	
	public Sistema() {
		this.usuarios = new ArrayList<Usuario>();
	}
	
	public List<Pelicula> getPeliculas() {
		return this.peliculas;	}
	
	public static Sistema getInstance() {
		if(instancia == null) {
			instancia = new Sistema();
		}
		
		return instancia;
	}
	
	
	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}
	
	public void addUsuario(Usuario u) {
		this.usuarios.add(u);
	}
	
	public void addPeliculas(Pelicula p1) {
		this.peliculas.add(p1);
	}
}
