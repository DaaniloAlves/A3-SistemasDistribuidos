package com.example.ControleFarmacia.Models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "O nome do produto não pode ser nulo.")
    @OneToOne
    @JoinColumn(name = "usuario_id") // chave estrangeira para a tabela Usuario
    private Usuario usuario;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarrinhoItem> itens;

    public void adicionarItem(CarrinhoItem item) {
        itens.add(item);
    }

    public void removerItem(CarrinhoItem item) {
        itens.remove(item);
    }

    public void limparCarrinho() {
        itens.clear();
    }
}
