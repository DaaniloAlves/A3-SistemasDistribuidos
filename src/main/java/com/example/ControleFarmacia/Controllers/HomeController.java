package com.example.ControleFarmacia.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
