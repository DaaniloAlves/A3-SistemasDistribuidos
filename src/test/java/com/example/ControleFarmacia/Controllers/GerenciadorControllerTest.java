package com.example.ControleFarmacia.Controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Repositories.ProdutoRepo;
import com.example.ControleFarmacia.Repositories.UsuarioRepo;
import com.example.ControleFarmacia.Services.ProdutoService;
import com.example.ControleFarmacia.Services.UsuarioService;

public class GerenciadorControllerTest {

    @Mock
    private ProdutoService produtoService;
    @Mock
    private ProdutoRepo produtoRepo;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepo usuarioRepo;

    @InjectMocks
    private GerenciadorController gerenciadorController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gerenciadorController).build();
    }

    @Test
    public void testTelaProdutos() throws Exception {
        // Mocking service method
        when(produtoService.findAll()).thenReturn(new ArrayList<>());
        
        // Perform request and verify the response
        mockMvc.perform(get("/Gerenciar/Produtos"))
                .andExpect(status().isOk())
                .andExpect(view().name("gerenciadorProdutos"))
                .andExpect(model().attributeExists("produtos"));
        
        verify(produtoService, times(1)).findAll();
    }

    @Test
    public void testTelaUsuarios() throws Exception {
        // Mocking service method
        when(usuarioService.findAll()).thenReturn(new ArrayList<>());
        
        mockMvc.perform(get("/Gerenciar/Usuarios"))
                .andExpect(status().isOk())
                .andExpect(view().name("gerenciadorUsuarios"))
                .andExpect(model().attributeExists("usuarios"));
        
        verify(usuarioService, times(1)).findAll();
    }

    @Test
    public void testEditarProduto() throws Exception {
        Produto produto = new Produto(1, "Produto Teste", "Descrição", 10, 100.0f);
        
        // Mocking service method
        when(produtoService.findById(1)).thenReturn(Optional.of(produto));

        mockMvc.perform(get("/Gerenciar/editar-produto/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editarProduto"))
                .andExpect(model().attribute("produto", produto));
        
        verify(produtoService, times(1)).findById(1);
    }

    @Test
    public void testAtualizarProduto() throws Exception {
        Produto produto = new Produto(1, "Produto Teste", "Descrição", 10, 100.0f);
        when(produtoService.findById(1)).thenReturn(Optional.of(produto));
        
        mockMvc.perform(post("/Gerenciar/editar-produto")
                        .param("id", "1")
                        .param("nome", "Produto Atualizado")
                        .param("descricao", "Descrição Atualizada")
                        .param("preco", "150.0")
                        .param("quantidade", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Gerenciar/Produtos"));
        
        verify(produtoRepo, times(1)).save(produto);
    }

    @Test
    public void testDeletarProduto() throws Exception {
        Produto produto = new Produto(1, "Produto Teste", "Descrição", 10, 100.0f);
        
        // Mocking the product deletion
        when(produtoRepo.findById(1)).thenReturn(Optional.of(produto));
        
        mockMvc.perform(get("/Gerenciar/deletar-produto/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Gerenciar/Produtos"));
        
        verify(produtoRepo, times(1)).delete(produto);
    }

    @Test
    public void testDesativarUsuario() throws Exception {
        Usuario usuario = new Usuario(1, "usuario1", "senha", "role", true);
        when(usuarioRepo.findById(1)).thenReturn(Optional.of(usuario));
        
        mockMvc.perform(get("/Gerenciar/desativar-usuario/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Gerenciar/Usuarios"));
        
        verify(usuarioRepo, times(1)).save(usuario);
    }

    @Test
    public void testAtivarUsuario() throws Exception {
        Usuario usuario = new Usuario(1, "usuario1", "senha", "role", false);
        when(usuarioRepo.findById(1)).thenReturn(Optional.of(usuario));
        
        mockMvc.perform(get("/Gerenciar/ativar-usuario/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Gerenciar/Usuarios"));
        
        verify(usuarioRepo, times(1)).save(usuario);
    }
}
