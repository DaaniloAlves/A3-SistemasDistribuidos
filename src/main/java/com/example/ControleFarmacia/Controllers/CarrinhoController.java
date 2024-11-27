package com.example.ControleFarmacia.Controllers;

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

import com.example.ControleFarmacia.Models.Carrinho;
import com.example.ControleFarmacia.Models.CarrinhoItem;
import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Services.CarrinhoService;
import com.example.ControleFarmacia.Services.ProdutoService;

@RestController
@RequestMapping("/Carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/Adicionar")
    public ResponseEntity<CarrinhoItem> adicionarAoCarrinho(@RequestParam int id) {
        Optional<Produto> produto = produtoService.findById(id);
        carrinhoService.adicionarProduto(1, id, 1);
        return null;
    }
}
