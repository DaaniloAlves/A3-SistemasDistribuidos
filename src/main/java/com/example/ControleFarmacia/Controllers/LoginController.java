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

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Login")
public class LoginController {

    @Autowired
    UsuarioService usuarioService;

        @GetMapping
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("loginIndex");
        return mv;
    }

    @PostMapping
    public ModelAndView logar(@RequestParam String login, @RequestParam String senha, HttpServletRequest request) {
        Optional<Usuario> usuarioOptional = usuarioService.buscarPorLoginESenha(login, senha); 
    if (usuarioOptional.isPresent()) {
        Usuario usuarioCompleto = usuarioOptional.get();
        request.getSession().setAttribute("user", usuarioCompleto); // Salva o usuário completo na sessão
        return new ModelAndView("redirect:/Home");
    }
    return new ModelAndView("loginIndex"); // Volta para a tela de login em caso de falha
    }

    
    @GetMapping("/Logout")
        public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/Login");
    }
}
