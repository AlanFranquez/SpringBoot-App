package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.classes.Pelicula;
import com.classes.Usuario;

@Controller
public class RegistrarPelicula {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String IMAGE_UPLOAD_DIR = "uploads/images";

    @GetMapping("/RegistrarPelicula")
    public String RegistrarPeliculaGET(HttpSession session, Model model) {
        if(session == null || session.getAttribute("usuarioLogueado") == null) {
            System.out.print("El usuario no se encuentra logueado");
            return "redirect:/home";
        }
        return "RegistrarPelicula";
    }

    @PostMapping("/RegistrarPelicula")
    @Transactional
    public String RegistrarPeliculaPOST(HttpSession session, @RequestParam("img") MultipartFile image, @RequestParam("title") String title, String sinopsis) throws IOException {
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            System.out.print("El usuario no se encuentra logueado");
            return "redirect:/login";
        }

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        Usuario u = entityManager.find(Usuario.class, usuarioLogueado.getUsername());
        

        File uploadDir = new File(IMAGE_UPLOAD_DIR);
        
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();  
        }

        String originalFilename = image.getOriginalFilename();
        
      
        Path path = Paths.get(uploadDir.getAbsolutePath(), originalFilename);

        
        image.transferTo(path.toFile());  

        String imagePath = IMAGE_UPLOAD_DIR + "/" + originalFilename;
        
        

        Pelicula nuevaPelicula = new Pelicula(title, imagePath, sinopsis);
        entityManager.persist(nuevaPelicula);

        return "redirect:/home"; 
    }
}
