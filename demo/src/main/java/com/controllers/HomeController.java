package com.controllers;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.websocket.Session;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.classes.*;

@Controller
public class HomeController {

    Sistema s = Factory.getSistema();

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/home")
    @Transactional
    public String home(HttpSession session, Model model) {
        String bienvenida = "Welcome, testing deploying with Spring Boot";
        
        if(session == null || session.getAttribute("usuarioLogueado") == null) {
        	System.out.print("El usuario no se encuentra logueado");
        } else {
        	System.out.print("El usuario se encuentra logueado");
        	return "redirect:/homeLogueado";
        }
        
        List<Usuario> users = entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        List<Pelicula> movies = entityManager.createQuery("SELECT p FROM Pelicula p", Pelicula.class).getResultList();
        
        model.addAttribute("bienvenida", bienvenida);
        model.addAttribute("users", users);
        model.addAttribute("movies", movies);
        return "home";  // Retorna la vista 'home'
    }
    
    @PostMapping("/login")
    public String postMethodName(String username, String password, HttpSession session) {
       
    	Usuario u = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.password = :password AND u.username = :username", Usuario.class)
    			.setParameter("username", username).setParameter("password", password).getResultStream()
                .findFirst()
                .orElse(null);
    	
    	if(u == null) {
    		return "redirect:/home";
    	}
    	

    	
        
       session.setAttribute("usuarioLogueado", u);
       return "redirect:/homeLogueado";
    }
    
    @GetMapping("/homeLogueado")
    public String homeLogueado(HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/home"; 
        }
        
        List<Pelicula> movies = entityManager.createQuery("SELECT p FROM Pelicula p", Pelicula.class).getResultList();

        model.addAttribute("usuario", usuarioLogueado);
        model.addAttribute("movies",movies);
        return "homeLogueado"; 
    }
    
    @PostMapping("/logout")
    public String logout(HttpSession session) {
    	if(session.getAttribute("usuarioLogueado") != null) {
    		session.invalidate();
    	}
    	
    	return "redirect:/home";
    }
    

    
}
