package com.example.ControleFarmacia.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.ControleFarmacia.models.Produto;
import com.example.ControleFarmacia.services.ProdutoService;

@RestController
@RequestMapping()
public class HomeController {
    
    @Autowired
    ProdutoService service;

    @GetMapping("/Home")
    private ModelAndView home() {
        ModelAndView mv = new ModelAndView("home");
        Iterable<Produto> produtosIterable = service.findAll();
        List<Produto> produtos = new ArrayList<>();
        produtosIterable.forEach(produtos::add);
        mv.addObject("produtos", produtos);
        return mv; 
        
        
    }
    

}
