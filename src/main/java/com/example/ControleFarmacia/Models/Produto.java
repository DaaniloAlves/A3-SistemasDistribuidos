package com.example.ControleFarmacia.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Produto {

    public Produto(int id, String nome, String desc, int qtd, float preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = desc;
        this.qtd = qtd;
        this.preco = preco;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "O nome do produto não pode estar vazio.")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    private String nome;
    @NotNull(message = "A quantidade não pode estar vazia.")
    private int qtd;
    @NotNull(message = "A descrição não pode estar vazia.")
    private String descricao;
    @NotNull(message = "O preço não pode esstar vazio.")
    private float preco;

    public void setQuantidade(int qtd) {
        this.qtd = qtd;
    }

    public float getPreco() {
        return this.preco;
    }
}
    
