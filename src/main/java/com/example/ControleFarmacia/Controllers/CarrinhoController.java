package com.example.ControleFarmacia.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Services.CarrinhoService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping("/Adicionar")
    public ModelAndView adicionarAoCarrinho(@RequestParam int id, HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("user");
        // Chama o service com o ID do usuário logado
        carrinhoService.adicionarProduto(usuarioLogado.getId(), id, 1);

        return new ModelAndView("redirect:/Home");
    }

    // Remover uma unidade do produto no carrinho
    @PostMapping("/RemoverUmaUnidade")
    public ModelAndView removerUmaUnidade(@RequestParam int itemId, HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("user");
        carrinhoService.removerUmaUnidadeDoProduto(usuarioLogado.getId(), itemId);
        return new ModelAndView("redirect:/Home");
    }

    // Remover todas as unidades de um produto do carrinho
    @PostMapping("/RemoverTodasUnidades")
    public ModelAndView removerTodasUnidades(@RequestParam int itemId, HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("user");
        carrinhoService.removerProdutoDoCarrinho(usuarioLogado.getId(), itemId);  // Reaproveitar o método que já existe para remover completamente
        return new ModelAndView("redirect:/Home");
    }

    // Limpar o carrinho de um usuário
    @PostMapping("/LimparCarrinho")
    public ModelAndView limparCarrinho(HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("user");
        carrinhoService.limparCarrinho(usuarioLogado.getId());
        return new ModelAndView("redirect:/Home");
    }
}
