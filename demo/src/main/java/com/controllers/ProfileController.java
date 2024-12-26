package com.controllers;

import java.net.http.HttpClient;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.classes.Pelicula;
import com.classes.Usuario;

import ch.qos.logback.core.model.Model;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

	@PersistenceContext
	private EntityManager em;
	
	@GetMapping("/profile/{id}")
	public String userProfile(HttpSession session, org.springframework.ui.Model model, @PathVariable long id) {
		
		Usuario u = em.find(Usuario.class, id);
		
		if(u == null) {
			return "redirect:/home";
		}
		
		Usuario usuarioLogueeado = (Usuario) session.getAttribute("usuarioLogueado");
		if(usuarioLogueeado == null) {
			return "redirect:/home";
		}
		
		
		model.addAttribute("user", u);
		return "perfilUsuario";
	}
}
 