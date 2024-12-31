package com.controllers;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classes.Pelicula;
import com.classes.Review;
import com.classes.Usuario;

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
	public String addMovie(HttpSession session, org.springframework.ui.Model model, @PathVariable long id, @RequestParam("text") String text) {
		
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
		
		model.addAttribute("id", uDB.getId());
		return "redirect:/profile/" + uDB.getId();
	}

	@PostMapping("/movies/review/{id}")
	@Transactional
	@ResponseBody
	public  ResponseEntity<Map<String, Object>> addReview(HttpSession session, Model m, @PathVariable long id, @RequestParam("text") String text) {
		
		Usuario u = (Usuario) session.getAttribute("usuarioLogueado");

		Usuario usDB = em.find(Usuario.class, u.getId());

		Pelicula p = em.find(Pelicula.class, id); 
		
		Review r = new Review(text, usDB);

		usDB.addReview(r);
		p.addReview(r);

		em.persist(r);

		Map<String, Object> jsonPersonalizado = new HashMap<>();

		jsonPersonalizado.put("likes", r.getLikes());
		jsonPersonalizado.put("author", r.getAuthor().getUsername());
		jsonPersonalizado.put("authorImage", r.getAuthor().getImage());
		jsonPersonalizado.put("text", r.getText());
		jsonPersonalizado.put("id", r.getId());
		jsonPersonalizado.put("date", r.getFormattedDate());

		return ResponseEntity.ok(jsonPersonalizado);
	}
}
