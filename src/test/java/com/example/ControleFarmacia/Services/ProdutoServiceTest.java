package com.example.ControleFarmacia.Services;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Repositories.ProdutoRepo;

public class ProdutoServiceTest {

    @Mock
    private ProdutoRepo produtoRepo;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produto = new Produto();
        produto.setId(1);
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do Produto");
        produto.setPreco(100.0f);
        produto.setQuantidade(10);
    }

    @Test
    void testAddProduto() {
        // Configurando o mock para salvar o produto
        when(produtoRepo.save(produto)).thenReturn(produto);

        Produto produtoSalvo = produtoService.add(produto);

        // Verificando se o produto foi salvo corretamente
        assertNotNull(produtoSalvo);
        assertEquals(produto.getId(), produtoSalvo.getId());
        assertEquals(produto.getNome(), produtoSalvo.getNome());

        // Verificando se o método save foi chamado uma vez
        verify(produtoRepo, times(1)).save(produto);
    }

    @Test
    void testFindById() {
        // Configurando o mock para retornar um produto quando procurado pelo ID
        when(produtoRepo.findById(1)).thenReturn(Optional.of(produto));

        Optional<Produto> produtoEncontrado = produtoService.findById(1);

        // Verificando se o produto foi encontrado
        assertTrue(produtoEncontrado.isPresent());
        assertEquals(produto.getId(), produtoEncontrado.get().getId());

        // Verificando se o método findById foi chamado uma vez
        verify(produtoRepo, times(1)).findById(1);
    }

    @Test
    void testFindByIdProdutoNaoEncontrado() {
        // Configurando o mock para retornar um Optional vazio quando não encontrar o produto
        when(produtoRepo.findById(1)).thenReturn(Optional.empty());

        Optional<Produto> produtoEncontrado = produtoService.findById(1);

        // Verificando se o produto não foi encontrado
        assertFalse(produtoEncontrado.isPresent());

        // Verificando se o método findById foi chamado uma vez
        verify(produtoRepo, times(1)).findById(1);
    }

    @Test
    void testFindAll() {
        // Configurando o mock para retornar uma lista de produtos
        Iterable<Produto> produtos = List.of(produto);
        when(produtoRepo.findAll()).thenReturn(produtos);

        Iterable<Produto> produtosListados = produtoService.findAll();

        // Verificando se a lista de produtos foi retornada corretamente
        assertNotNull(produtosListados);
        assertTrue(produtosListados.iterator().hasNext());

        // Verificando se o método findAll foi chamado uma vez
        verify(produtoRepo, times(1)).findAll();
    }
}

