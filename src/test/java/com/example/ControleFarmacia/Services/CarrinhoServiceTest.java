package com.example.ControleFarmacia.Services;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.ControleFarmacia.Models.Carrinho;
import com.example.ControleFarmacia.Models.CarrinhoItem;
import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Repositories.CarrinhoItemRepo;
import com.example.ControleFarmacia.Repositories.CarrinhoRepo;
import com.example.ControleFarmacia.Repositories.ProdutoRepo;
import com.example.ControleFarmacia.Repositories.UsuarioRepo;

public class CarrinhoServiceTest {

    @Mock
    private CarrinhoItemRepo carrinhoItemRepo;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepo produtoRepo;

    @Mock
    private UsuarioRepo usuarioRepo;

    @Mock
    private CarrinhoRepo carrinhoRepo;

    @InjectMocks
    private CarrinhoService carrinhoService;

    private Usuario usuario;
    private Produto produto;
    private Carrinho carrinho;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Criação de objetos simulados
        usuario = new Usuario();
        usuario.setId(1);
        
        carrinho = new Carrinho();
        carrinho.setId(1);
        usuario.setCarrinho(carrinho);
        
        produto = new Produto();
        produto.setId(1);
        produto.setNome("Produto 1");
        produto.setPreco(100.0f);
        produto.setQuantidade(10);

        // Mockando os repositórios
        when(usuarioRepo.findById(1)).thenReturn(Optional.of(usuario));
        when(produtoRepo.findById(1)).thenReturn(Optional.of(produto));
        when(produtoService.findById(1)).thenReturn(Optional.of(produto));
        when(carrinhoItemRepo.save(any(CarrinhoItem.class))).thenReturn(new CarrinhoItem());
    }

    @Test
    public void testAdicionarProdutoCarrinho_Sucesso() {
        CarrinhoItem carrinhoItem = carrinhoService.adicionarProduto(1, 1, 2);
        
        // Verificar se o método save foi chamado
        verify(carrinhoItemRepo).save(any(CarrinhoItem.class));

        // Verificar se o estoque foi atualizado
        assertEquals(8, produto.getQuantidade());
    }

    @Test
    public void testAdicionarProdutoCarrinho_ProdutoNaoEncontrado() {
        when(produtoService.findById(2)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carrinhoService.adicionarProduto(1, 2, 2);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
    }

    @Test
    public void testAdicionarProdutoCarrinho_EstoqueInsuficiente() {
        when(produtoService.findById(1)).thenReturn(Optional.of(produto));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carrinhoService.adicionarProduto(1, 1, 20);
        });

        assertEquals("Estoque insuficiente para este produto", exception.getMessage());
    }

    @Test
    public void testListarItensDoCarrinho() {
        // Simula um item no carrinho
        CarrinhoItem item = new CarrinhoItem();
        item.setProduto(produto);
        item.setQuantidade(2);
        
        carrinho.getItens().add(item);

        when(carrinhoItemRepo.findByCarrinhoId(1)).thenReturn(carrinho.getItens());

        // Verificar se o item do carrinho foi listado corretamente
        var itens = carrinhoService.listarItensDoCarrinho(1);
        assertEquals(1, itens.size());
        assertEquals(produto, itens.get(0).getProduto());
    }

    @Test
    public void testRemoverProdutoDoCarrinho() {
        CarrinhoItem item = new CarrinhoItem();
        item.setProduto(produto);
        item.setQuantidade(2);
        
        carrinho.getItens().add(item);
        when(carrinhoItemRepo.findById(item.getId())).thenReturn(Optional.of(item));
        
        carrinhoService.removerProdutoDoCarrinho(1, item.getId());
        
        verify(carrinhoItemRepo).delete(item);
        assertEquals(12, produto.getQuantidade()); // Verifica se o estoque foi atualizado
    }

    @Test
    public void testRemoverUmaUnidadeDoProduto() {
        CarrinhoItem item = new CarrinhoItem();
        item.setProduto(produto);
        item.setQuantidade(5);
        
        carrinho.getItens().add(item);
        when(carrinhoItemRepo.findById(item.getId())).thenReturn(Optional.of(item));
        
        carrinhoService.removerUmaUnidadeDoProduto(1, item.getId());
        
        verify(carrinhoItemRepo).save(item);
        assertEquals(11, produto.getQuantidade()); // Verifica se o estoque foi atualizado
        assertEquals(4, item.getQuantidade()); // Verifica se a quantidade no carrinho foi atualizada
    }

    @Test
    public void testLimparCarrinho() {
        CarrinhoItem item = new CarrinhoItem();
        item.setProduto(produto);
        item.setQuantidade(2);

        carrinho.getItens().add(item);
        when(carrinhoItemRepo.findByCarrinhoId(1)).thenReturn(carrinho.getItens());

        carrinhoService.limparCarrinho(1);

        verify(carrinhoItemRepo).deleteAllByCarrinhoIdAndQuantidade(1, 0);
        assertEquals(12, produto.getQuantidade()); // Verifica se o estoque foi atualizado
    }
}
