package com.example.ControleFarmacia.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Services.CarrinhoService;
import com.example.ControleFarmacia.Services.ProdutoService;

@RestController
@RequestMapping()
public class HomeController {
    
    @Autowired
    ProdutoService produtoService;

    @Autowired
    CarrinhoService carrinhoService;
@GetMapping("/teste")
public void exibirUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            System.out.println("Usuário logado: " + username);
        }
    }

    @GetMapping("/Home")
    private ModelAndView home() {

       

        ModelAndView mv = new ModelAndView("home");
        Iterable<Produto> produtosIterable = produtoService.findAll();
        List<Produto> produtos = new ArrayList<>();
        // List<CarrinhoItem> produtosCarrinho = carrinhoService.listarItensDoCarrinho(usuarioId);
        produtosIterable.forEach(produtos::add);
        mv.addObject("produtos", produtos);
        return mv; 
        
        
    }
    

}
