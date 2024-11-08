package com.example.ControleFarmacia.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/Cadastro")
public class CadastroItensController {
    @GetMapping()
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("cadastroIndex");
        return mv;
    }
}
