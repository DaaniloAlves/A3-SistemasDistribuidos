package com.example.ControleFarmacia.Models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarrinhoItemTest {

    private Produto produto;
    private Carrinho carrinho;

    @BeforeEach
    public void setUp() {
        // Inicializando os objetos necessários
        produto = new Produto(1, "Produto A", "Descrição A", 100, 10.0f);
        carrinho = new Carrinho();
    }

    @Test
    public void testConstrutorComProdutoEQuantidade() {
        // Criando um CarrinhoItem usando o construtor com Produto e Quantidade
        CarrinhoItem carrinhoItem = new CarrinhoItem(produto, 2);

        // Verificando se os valores estão corretamente atribuídos
        assertEquals(produto, carrinhoItem.getProduto());
        assertEquals(2, carrinhoItem.getQuantidade());
        assertEquals(produto.getPreco(), carrinhoItem.getPrecoUnitario(), 0.01);
    }

    @Test
    public void testConstrutorComIdCarrinhoProdutoEQuantidade() {
        // Criando um CarrinhoItem usando o construtor com ID, Carrinho, Produto e Quantidade
        CarrinhoItem carrinhoItem = new CarrinhoItem(1, carrinho, produto, 3);

        // Verificando se os valores estão corretamente atribuídos
        assertEquals(1, carrinhoItem.getId());
        assertEquals(carrinho, carrinhoItem.getCarrinho());
        assertEquals(produto, carrinhoItem.getProduto());
        assertEquals(3, carrinhoItem.getQuantidade());
    }

    @Test
    public void testGetPrecoUnitario() {
        // Testando se o preço unitário é atribuído corretamente
        CarrinhoItem carrinhoItem = new CarrinhoItem(produto, 1);

        // O preço unitário deve ser igual ao preço do produto
        assertEquals(10.0f, carrinhoItem.getPrecoUnitario(), 0.01);
    }

    @Test
    public void testSettersAndGetters() {
        // Testando os métodos setters e getters
        CarrinhoItem carrinhoItem = new CarrinhoItem();
        carrinhoItem.setId(1);
        carrinhoItem.setCarrinho(carrinho);
        carrinhoItem.setProduto(produto);
        carrinhoItem.setQuantidade(4);
        carrinhoItem.setPrecoUnitario(10.0f);

        assertEquals(1, carrinhoItem.getId());
        assertEquals(carrinho, carrinhoItem.getCarrinho());
        assertEquals(produto, carrinhoItem.getProduto());
        assertEquals(4, carrinhoItem.getQuantidade());
        assertEquals(10.0f, carrinhoItem.getPrecoUnitario(), 0.01);
    }

    @Test
    public void testNotNullAttributes() {
        // Testando se as anotações @NotNull estão sendo aplicadas corretamente
        // Aqui, estamos verificando se não há valores nulos para os atributos obrigatórios

        // Criando um CarrinhoItem sem Carrinho (que deve falhar se o @NotNull for respeitado)
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new CarrinhoItem(null, produto, 2);  // Carrinho é null
        });
        assertNotNull(exception);

        // Criando um CarrinhoItem sem Produto (que deve falhar se o @NotNull for respeitado)
        exception = assertThrows(NullPointerException.class, () -> {
            new CarrinhoItem(carrinho, null, 2);  // Produto é null
        });
        assertNotNull(exception);
    }
}
