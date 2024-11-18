package com.example.ControleFarmacia.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ControleFarmacia.models.Carrinho;
import com.example.ControleFarmacia.models.CarrinhoItem;
import com.example.ControleFarmacia.models.Produto;
import com.example.ControleFarmacia.services.CarrinhoService;
import com.example.ControleFarmacia.services.ProdutoService;

@RestController
@RequestMapping("/Home/Carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoService produtoService;

    // Endpoint para criar um carrinho para um usuário
    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<Carrinho> criarCarrinho(@PathVariable int usuarioId) {
        Carrinho carrinho = carrinhoService.criarCarrinho(usuarioId);
        return new ResponseEntity<>(carrinho, HttpStatus.CREATED);
    }

    // Endpoint para adicionar um produto ao carrinho de um usuário
    @PostMapping("/usuario/{usuarioId}/produto/{produtoId}")
    public ResponseEntity<CarrinhoItem> adicionarProdutoAoCarrinho(
            @PathVariable int usuarioId,
            @PathVariable int produtoId,
            @RequestParam int quantidade) {

        // Verifica se o produto existe
        Optional<Produto> produto = produtoService.findById(produtoId);
        if (produto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CarrinhoItem carrinhoItem = carrinhoService.adicionarProduto(usuarioId, produtoId, quantidade);
        return new ResponseEntity<>(carrinhoItem, HttpStatus.CREATED);
    }

    // Endpoint para listar os itens do carrinho de um usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CarrinhoItem>> listarItensDoCarrinho(@PathVariable int usuarioId) {
        List<CarrinhoItem> itens = carrinhoService.listarItensDoCarrinho(usuarioId);
        return itens.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(itens, HttpStatus.OK);
    }

    // Endpoint para remover um item do carrinho de um usuário
    @DeleteMapping("/usuario/{usuarioId}/item/{itemId}")
    public ResponseEntity<Void> removerProdutoDoCarrinho(
            @PathVariable int usuarioId,
            @PathVariable int itemId) {
        carrinhoService.removerProdutoDoCarrinho(usuarioId, itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint para limpar o carrinho de um usuário
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> limparCarrinho(@PathVariable int usuarioId) {
        carrinhoService.limparCarrinho(usuarioId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
