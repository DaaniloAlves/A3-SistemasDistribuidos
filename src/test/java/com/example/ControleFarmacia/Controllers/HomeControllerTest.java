package com.example.ControleFarmacia.Controllers;

import java.util.Arrays;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Services.CarrinhoService;
import com.example.ControleFarmacia.Services.ProdutoService;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private CarrinhoService carrinhoService;

    @InjectMocks
    private HomeController homeController;

    private Produto produto1;
    private Produto produto2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        produto1 = new Produto(1, "Produto A", "Descrição A", 10, 5.0f);
        produto2 = new Produto(2, "Produto B", "Descrição B", 20, 10.0f);
    }

    @Test
    public void testIndex() throws Exception {
        // Mock do retorno do serviço
        when(produtoService.findAll()).thenReturn(Arrays.asList(produto1, produto2));

        // Realiza o teste
        mockMvc.perform(MockMvcRequestBuilders.get("/Home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("produtos"))
                .andExpect(model().attribute("produtos", Arrays.asList(produto1, produto2)));

        // Verifica que o serviço foi chamado uma vez
        verify(produtoService, times(1)).findAll();
    }
}
