package com.example.ControleFarmacia.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Services.UsuarioService;

@RestController
@RequestMapping("/Logar")
public class UsuarioController {
    
    @Autowired
    UsuarioService service;

    @GetMapping
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("loginIndex");
        return mv;
    }

    @PostMapping
    public ModelAndView logar(@RequestParam String login, @RequestParam String senha) {
        Usuario usuarioVerificado = new Usuario(login, senha);
        Optional<Usuario> usuarioOptional = Optional.of(usuarioVerificado);
        if(service.usuarioExiste(usuarioOptional)) {
            return new ModelAndView("redirect:/Home");
        }
        return new ModelAndView("loginIndex");
    }
}
