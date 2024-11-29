package com.example.ControleFarmacia.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.ControleFarmacia.Models.Carrinho;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Services.UsuarioService;

@RestController
@RequestMapping("/Usuario")
public class UsuarioController {
    
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/Cadastrar")
    public ModelAndView cadastrarUsuario() {
        return new ModelAndView("cadastroUsuario");
    }

     @PostMapping("/Cadastrar")
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestParam String username, @RequestParam String password, @RequestParam(defaultValue = "false") boolean admin) {
        // Define a role com base no checkbox
        String role = admin ? "ADMIN" : "USER";
        Usuario usuario = new Usuario(username, password, role);
        usuarioService.criarUsuario(usuario);
        // Cria e associa um carrinho ao usuário
        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario);
        usuario.setCarrinho(carrinho);
        usuarioService.salvarUsuarioComCarrinho(usuario, carrinho);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }
}
