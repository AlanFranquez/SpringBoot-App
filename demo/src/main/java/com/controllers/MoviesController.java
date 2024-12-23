package com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.classes.Pelicula;

import ch.qos.logback.core.model.Model;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Controller
public class MoviesController {

	@PersistenceContext
	private EntityManager em;
	
	@GetMapping("/movies/{id}")
	public String movieProfile(org.springframework.ui.Model model, @PathVariable long id) {
		
		Pelicula p = em.find(Pelicula.class, id);
		
		if(p == null) {
			System.out.print("Movie hasn't been found, keep trying");
			return "redirect:/home";
		}
		
		
		model.addAttribute("tituloPelicula", p.getTitulo());
		model.addAttribute("descPelicula", p.getSinopsis());
		model.addAttribute("posterPelicula", p.getImagen());
		model.addAttribute("fechaPelicula", p.getFecha());
		
		return "perfilPelicula";
	}
}
