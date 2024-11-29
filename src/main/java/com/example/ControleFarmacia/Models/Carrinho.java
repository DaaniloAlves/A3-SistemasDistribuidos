package com.example.ControleFarmacia.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(mappedBy = "carrinho", cascade = CascadeType.ALL)
     @JsonIgnoreProperties("carrinho")
    private Usuario usuario;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarrinhoItem> itens;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void adicionarItem(CarrinhoItem item) {
        itens.add(item);
    }

    public void removerItem(CarrinhoItem item) {
        itens.remove(item);
    }

    public void limparCarrinho() {
        itens.clear();
    }

    public List<CarrinhoItem> getItens() {
        return itens;
    }
}
