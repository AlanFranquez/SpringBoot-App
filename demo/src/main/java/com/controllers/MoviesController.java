package com.controllers;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.classes.Pelicula;
import com.classes.Usuario;

import ch.qos.logback.core.model.Model;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class MoviesController {

	@PersistenceContext
	private EntityManager em;
	
	@GetMapping("/movies/{id}")
	public String movieProfile(HttpSession session, org.springframework.ui.Model model, @PathVariable long id) {
		
		Pelicula p = em.find(Pelicula.class, id);
		
		if(p == null) {
			System.out.print("Movie hasn't been found, keep trying");
			return "redirect:/home";
		}
		
		Usuario s = (Usuario) session.getAttribute("usuarioLogueado");
		
		if(s == null) {
			return "redirect:/home";
		}

		Boolean alreadyAdded = false;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		String fechaFormateada = p.getFecha().format(formatter);
		
		Usuario us = em.find(Usuario.class, s.getId());
		
		List<Pelicula> moviesAdded = us.getMovies();
		
		for(Pelicula pel : moviesAdded) {
			if(pel.getTitulo() == p.getTitulo()) {
				alreadyAdded = true;
			}
		}
		
		model.addAttribute("movie", p);
		model.addAttribute("user", s);
		model.addAttribute("added", alreadyAdded);
		model.addAttribute("fechaForm", fechaFormateada);
		return "perfilPelicula";
	}
	
	@PostMapping("/movies/{id}")
	@Transactional
	public String addMovie(HttpSession session, org.springframework.ui.Model model, @PathVariable long id) {
		
		Pelicula p = em.find(Pelicula.class, id);
		
		if(p == null) {
			return "redirect:/home";
		}
		
		
		
		
		Usuario userLogueado = (Usuario) session.getAttribute("usuarioLogueado");
		if(userLogueado == null) {
			return "redirect:/home";
		}
		
		Usuario uDB = em.find(Usuario.class, userLogueado.getId());
		
		
		
		uDB.addPelicula(p);
		
		
		return "redirect:/profile/" + uDB.getId();
	}
}
