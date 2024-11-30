package com.example.ControleFarmacia.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Carrinho {

    public Carrinho() {
        this.itens = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "usuario_id") // Define o nome da coluna no banco
    @JsonIgnoreProperties("carrinho")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarrinhoItem> itens;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void adicionarItem(CarrinhoItem item) {
        if (item != null) itens.add(item);
        
    }

    public void removerItem(CarrinhoItem item) {
        if (item != null) itens.remove(item);
    }

    public void limparCarrinho() {
        itens.clear();
    }

    public List<CarrinhoItem> getItens() {
        return itens;
    }
}
