package com.example.ControleFarmacia.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Services.CarrinhoService;

@RestController
@RequestMapping("/home/carrinho")
public class CarrinhoController {

@Autowired
CarrinhoService service;

    @GetMapping
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("carrinhoIndex");
        List<Produto> produtos = service.listarProdutos();
        mv.addObject("carrinho", produtos);
        return mv;
    }

    @PostMapping("/adicionar")
    public String adicionarProduto(@RequestParam int id, @RequestParam String nome, @RequestParam String desc, @RequestParam int qtd) {
        Produto produto = new Produto;

        service.adicionarProduto(produto);
        return "redirect:/carrinho";
    }
