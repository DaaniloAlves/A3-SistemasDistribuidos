package com.example.ControleFarmacia.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ControleFarmacia.Models.CarrinhoItem;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Services.CarrinhoService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping("/Adicionar")
    public ResponseEntity<CarrinhoItem> adicionarAoCarrinho(@RequestParam int id, HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("user");
        // Chama o service com o ID do usuário logado
        CarrinhoItem itemAdicionado = carrinhoService.adicionarProduto(usuarioLogado.getId(), id, 1);

        return ResponseEntity.ok(itemAdicionado);
    }

    // Remover uma unidade do produto no carrinho
    @PostMapping("/RemoverUmaUnidade")
    public ResponseEntity<Void> removerUmaUnidade(@RequestParam int itemId, HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("user");
        carrinhoService.removerUmaUnidadeDoProduto(usuarioLogado.getId(), itemId);
        return ResponseEntity.ok().build();
    }

    // Remover todas as unidades de um produto do carrinho
    @PostMapping("/RemoverTodasUnidades")
    public ResponseEntity<Void> removerTodasUnidades(@RequestParam int itemId, HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("user");
        carrinhoService.removerProdutoDoCarrinho(usuarioLogado.getId(), itemId);  // Reaproveitar o método que já existe para remover completamente
        return ResponseEntity.ok().build();
    }

    // Limpar o carrinho de um usuário
    @PostMapping("/LimparCarrinho")
    public ResponseEntity<Void> limparCarrinho(HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("user");
        carrinhoService.limparCarrinho(usuarioLogado.getId());
        return ResponseEntity.ok().build();
    }
}
