package com.example.ControleFarmacia.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.ControleFarmacia.Models.Carrinho;
import com.example.ControleFarmacia.Models.CarrinhoItem;
import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Repositories.CarrinhoItemRepo;
import com.example.ControleFarmacia.Repositories.CarrinhoRepo;
import com.example.ControleFarmacia.Repositories.UsuarioRepo;

public class CarrinhoServiceTest {

    @Mock
    private CarrinhoItemRepo carrinhoItemRepo;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private UsuarioRepo usuarioRepo;

    @Mock
    private CarrinhoRepo carrinhoRepo;

    @InjectMocks
    private CarrinhoService carrinhoService;

    private Usuario usuario;
    private Produto produto;
    private Carrinho carrinho;
    private CarrinhoItem carrinhoItem;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando um usuário de exemplo
        usuario = new Usuario();
        usuario.setId(1);

        // Criando um carrinho de exemplo
        carrinho = new Carrinho();
        carrinho.setId(1);
        carrinho.setUsuario(usuario);
        usuario.setCarrinho(carrinho);

        // Criando um produto de exemplo
        produto = new Produto();
        produto.setId(1);
        produto.setNome("Produto Teste");

        // Criando um item de carrinho de exemplo
        carrinhoItem = new CarrinhoItem();
        carrinhoItem.setProduto(produto);
        carrinhoItem.setQuantidade(5);
        carrinhoItem.setCarrinho(carrinho);
    }

    @Test
    public void testAdicionarProduto_CriarNovoItem() {
        when(usuarioRepo.findById(1)).thenReturn(Optional.of(usuario));
        when(produtoService.findById(1)).thenReturn(Optional.of(produto));
        when(carrinhoItemRepo.save(any(CarrinhoItem.class))).thenReturn(carrinhoItem);

        CarrinhoItem resultado = carrinhoService.adicionarProduto(1, 1, 5);

        assertNotNull(resultado);
        assertEquals(5, resultado.getQuantidade());
        verify(carrinhoItemRepo, times(1)).save(any(CarrinhoItem.class));
    }

    @Test
    public void testAdicionarProduto_IncrementarQuantidade() {
        carrinho.getItens().add(carrinhoItem); // Adiciona o item ao carrinho

        when(usuarioRepo.findById(1)).thenReturn(Optional.of(usuario));
        when(produtoService.findById(1)).thenReturn(Optional.of(produto));
        when(carrinhoItemRepo.save(any(CarrinhoItem.class))).thenReturn(carrinhoItem);

        CarrinhoItem resultado = carrinhoService.adicionarProduto(1, 1, 3);

        assertNotNull(resultado);
        assertEquals(8, resultado.getQuantidade()); // A quantidade foi incrementada
        verify(carrinhoItemRepo, times(1)).save(any(CarrinhoItem.class));
    }

    @Test
    public void testListarItensDoCarrinho() {
        carrinho.getItens().add(carrinhoItem);

        when(usuarioRepo.findById(1)).thenReturn(Optional.of(usuario));
        when(carrinhoItemRepo.findAll()).thenReturn(Collections.singletonList(carrinhoItem));

        List<CarrinhoItem> itens = carrinhoService.listarItensDoCarrinho(1);

        assertNotNull(itens);
        assertEquals(1, itens.size());
        assertEquals(produto.getNome(), itens.get(0).getProduto().getNome());
    }

    @Test
    public void testRemoverProdutoDoCarrinho() {
        carrinho.getItens().add(carrinhoItem);

        when(carrinhoItemRepo.findById(1)).thenReturn(Optional.of(carrinhoItem));
        doNothing().when(carrinhoItemRepo).delete(any(CarrinhoItem.class));

        carrinhoService.removerProdutoDoCarrinho(1, 1);

        verify(carrinhoItemRepo, times(1)).delete(any(CarrinhoItem.class));
    }

    @Test
    public void testRemoverUmaUnidadeDoProduto() {
        when(carrinhoItemRepo.findById(1)).thenReturn(Optional.of(carrinhoItem));
        when(carrinhoItemRepo.save(any(CarrinhoItem.class))).thenReturn(carrinhoItem);

        carrinhoService.removerUmaUnidadeDoProduto(1, 1);

        assertEquals(4, carrinhoItem.getQuantidade());  // Quantidade foi reduzida
        verify(carrinhoItemRepo, times(1)).save(any(CarrinhoItem.class));
    }

    @Test
    public void testRemoverUmaUnidadeDoProduto_RemoverItemComQuantidade1() {
        carrinhoItem.setQuantidade(1); // A quantidade é 1, então o item será removido

        when(carrinhoItemRepo.findById(1)).thenReturn(Optional.of(carrinhoItem));
        doNothing().when(carrinhoItemRepo).delete(any(CarrinhoItem.class));

        carrinhoService.removerUmaUnidadeDoProduto(1, 1);

        verify(carrinhoItemRepo, times(1)).delete(any(CarrinhoItem.class));
    }

    @Test
    public void testLimparCarrinho() {
        carrinho.getItens().add(carrinhoItem);

        when(usuarioRepo.findById(1)).thenReturn(Optional.of(usuario));
        doNothing().when(carrinhoItemRepo).deleteAll();

        carrinhoService.limparCarrinho(1);

        verify(carrinhoItemRepo, times(1)).deleteAll();
    }

}
