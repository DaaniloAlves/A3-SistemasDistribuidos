package com.example.ControleFarmacia.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Repositories.CarrinhoRepo;

public class CarrinhoService {

    private final List<Produto> carrinho = new ArrayList<>();

    @Autowired
    CarrinhoRepo repo;

    public void adicionarProduto(Produto produto) {
        carrinho.add(produto);
    }

    public void removerProduto(int id) {
        carrinho.removeIf(produto -> produto.getId() == id);
    }

    public List<Produto> listarProdutos() {
        return new ArrayList<>(carrinho); // Retorna uma cópia para evitar modificações externas
    }

    public void limparCarrinho() {
        carrinho.clear();
    }

}
