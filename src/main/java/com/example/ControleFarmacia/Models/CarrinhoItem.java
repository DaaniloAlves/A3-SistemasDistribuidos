package com.example.ControleFarmacia.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CarrinhoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "carrinho_id")
    @JsonIgnoreProperties("itens")
    private Carrinho carrinho;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private int quantidade;
    private float precoUnitario;

    public CarrinhoItem(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }
    public CarrinhoItem() {

    }
    public CarrinhoItem(int id, Carrinho carrinho, Produto produto, int quantidade) {
        this.id = id;
        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
    }
}
