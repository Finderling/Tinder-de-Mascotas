/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejemplo1.RedSocialMascota1.controladores;

import com.ejemplo1.RedSocialMascota1.entidades.Usuario;
import com.ejemplo1.RedSocialMascota1.errores.ErrorServicio;
import com.ejemplo1.RedSocialMascota1.servicios.VotoServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Oficina
 */

@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
@Controller
@RequestMapping("votos")
public class VotoController {
    
    @Autowired
    private VotoServicio votoServicio;
    
    @GetMapping("/crear-voto")
    public String CrearVoto(ModelMap modelo,HttpSession session, @RequestParam String id,@RequestParam String id2) throws ErrorServicio{
             Usuario login = (Usuario) session.getAttribute("usuariosession");
             votoServicio.votar(login.getId(),id,id2);
       
     return "redirect:/inicio";
    }

}
