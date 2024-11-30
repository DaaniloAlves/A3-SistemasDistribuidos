package com.example.ControleFarmacia.Models;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarrinhoTest {

    private Carrinho carrinho;
    private CarrinhoItem item1;
    private CarrinhoItem item2;

    @BeforeEach
    public void setUp() {
        // Criação de carrinho e itens antes de cada teste
        carrinho = new Carrinho();
        item1 = new CarrinhoItem();  // Presumindo que CarrinhoItem tem um construtor padrão
        item2 = new CarrinhoItem();
    }

    @Test
    public void testAdicionarItem() {
        carrinho.adicionarItem(item1);
        List<CarrinhoItem> itens = carrinho.getItens();

        // Verifica se o carrinho tem um item após a adição
        assertEquals(1, itens.size());
        assertTrue(itens.contains(item1));
    }

    @Test
    public void testRemoverItem() {
        carrinho.adicionarItem(item1);
        carrinho.adicionarItem(item2);

        // Verifica que os dois itens foram adicionados
        assertEquals(2, carrinho.getItens().size());

        carrinho.removerItem(item1);

        // Verifica se o item1 foi removido corretamente
        assertEquals(1, carrinho.getItens().size());
        assertFalse(carrinho.getItens().contains(item1));
        assertTrue(carrinho.getItens().contains(item2));
    }

    @Test
    public void testLimparCarrinho() {
        carrinho.adicionarItem(item1);
        carrinho.adicionarItem(item2);

        // Verifica se o carrinho tem itens antes de limpar
        assertEquals(2, carrinho.getItens().size());

        carrinho.limparCarrinho();

        // Verifica se o carrinho está vazio após limpar
        assertEquals(0, carrinho.getItens().size());
    }

    @Test
    public void testAdicionarItemComNull() {
        carrinho.adicionarItem(null);

        // Verifica que o carrinho não contém itens nulos
        assertEquals(0, carrinho.getItens().size());
    }
}
