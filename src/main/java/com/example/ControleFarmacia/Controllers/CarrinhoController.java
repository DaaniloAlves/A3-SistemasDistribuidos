package com.example.ControleFarmacia.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/home/carrinho")
public class CarrinhoController {
    @GetMapping
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("carrinhoIndex");
        return mv;
    }
}
