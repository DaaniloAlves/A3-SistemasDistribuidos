package com.example.ControleFarmacia.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.ControleFarmacia.Models.CarrinhoItem;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Services.CarrinhoService;

@WebMvcTest(CarrinhoController.class)
public class CarrinhoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarrinhoService carrinhoService;

    @InjectMocks
    private CarrinhoController carrinhoController;

    private Usuario usuarioLogado;
    private CarrinhoItem carrinhoItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        usuarioLogado = new Usuario(1, "admin", "password", "ADMIN");
        carrinhoItem = new CarrinhoItem(1, null, null, 1); // Simplificando o item
    }

    @Test
    public void testAdicionarAoCarrinho() throws Exception {
        // Simula o comportamento do serviço
        when(carrinhoService.adicionarProduto(usuarioLogado.getId(), 1, 1)).thenReturn(carrinhoItem);

        // Realiza o teste
        mockMvc.perform(MockMvcRequestBuilders.post("/Carrinho/Adicionar")
                        .param("id", "1")
                        .sessionAttr("user", usuarioLogado)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carrinhoItem.getId()));

        // Verifica que o serviço foi chamado corretamente
        verify(carrinhoService, times(1)).adicionarProduto(usuarioLogado.getId(), 1, 1);
    }

    @Test
    public void testRemoverUmaUnidade() throws Exception {
        // Realiza o teste para remover uma unidade
        mockMvc.perform(MockMvcRequestBuilders.post("/Carrinho/RemoverUmaUnidade")
                        .param("itemId", "1")
                        .sessionAttr("user", usuarioLogado)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        // Verifica que o método foi chamado
        verify(carrinhoService, times(1)).removerUmaUnidadeDoProduto(usuarioLogado.getId(), 1);
    }

    @Test
    public void testRemoverTodasUnidades() throws Exception {
        // Realiza o teste para remover todas as unidades
        mockMvc.perform(MockMvcRequestBuilders.post("/Carrinho/RemoverTodasUnidades")
                        .param("itemId", "1")
                        .sessionAttr("user", usuarioLogado)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        // Verifica que o método foi chamado
        verify(carrinhoService, times(1)).removerProdutoDoCarrinho(usuarioLogado.getId(), 1);
    }

    @Test
    public void testLimparCarrinho() throws Exception {
        // Realiza o teste para limpar o carrinho
        mockMvc.perform(MockMvcRequestBuilders.post("/Carrinho/LimparCarrinho")
                        .sessionAttr("user", usuarioLogado)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        // Verifica que o método foi chamado
        verify(carrinhoService, times(1)).limparCarrinho(usuarioLogado.getId());
    }
}
