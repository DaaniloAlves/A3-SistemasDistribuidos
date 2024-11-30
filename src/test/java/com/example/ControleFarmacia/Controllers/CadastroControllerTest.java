package com.example.ControleFarmacia.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Services.ProdutoService;
import com.example.ControleFarmacia.Services.UsuarioService;

@WebMvcTest(CadastroController.class)
public class CadastroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private UsuarioService usuarioService;

    private Usuario usuarioAdmin;
    private Usuario usuarioComum;

    @BeforeEach
    public void setup() {
        usuarioAdmin = new Usuario("admin", "password", "ADMIN");
        usuarioComum = new Usuario("user", "password", "USER");
    }

    // Teste: GET /Cadastro/Produto - Usuário sem permissão
    @Test
    public void testProdutoCadastro_UsuarioSemPermissao() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/Cadastro/Produto")
                        .sessionAttr("user", usuarioComum))
                .andExpect(status().is3xxRedirection()) // Espera redirecionamento
                .andExpect(redirectedUrl("/erroPermissao")); // Redireciona para /erroPermissao
    }

    // Teste: GET /Cadastro/Produto - Usuário com permissão
    @Test
    public void testProdutoCadastro_UsuarioAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/Cadastro/Produto")
                        .sessionAttr("user", usuarioAdmin))
                .andExpect(status().isOk()) // Espera status 200 OK
                .andExpect(view().name("cadastroIndex"));
    }

    // Teste: POST /Cadastro/Produto - Usuário sem permissão
    @Test
    public void testProdutoCadastroPost_UsuarioSemPermissao() throws Exception {
        MockMultipartFile imagem = new MockMultipartFile("img", "produto.jpg", "image/jpeg", "fake-image".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/Cadastro/Produto")
                        .file(imagem)
                        .sessionAttr("user", usuarioComum)
                        .param("name", "Produto Teste")
                        .param("desc", "Descrição Teste")
                        .param("qtd", "10")
                        .param("price", "20.0"))
                .andExpect(status().isForbidden()); // Espera status 403
    }

    // Teste: POST /Cadastro/Produto - Usuário com permissão
    @Test
public void testProdutoCadastroPost_UsuarioAdmin() throws Exception {
    MockMultipartFile imagem = new MockMultipartFile("img", "produto.jpg", "image/jpeg", "fake-image".getBytes());

    // Configura o mock para aceitar a chamada do método
    Mockito.doAnswer(invocation -> null).when(produtoService).add(Mockito.any(Produto.class));

    mockMvc.perform(MockMvcRequestBuilders.multipart("/Cadastro/Produto")
                    .file(imagem)
                    .sessionAttr("user", usuarioAdmin)
                    .param("name", "Produto Teste")
                    .param("desc", "Descrição Teste")
                    .param("qtd", "10")
                    .param("price", "20.0"))
            .andExpect(status().isCreated()) // Espera status 201 Created
            .andExpect(jsonPath("$.nome").value("Produto Teste"));
}

    // Teste: GET /Cadastro/Usuario - Usuário sem permissão
    @Test
public void testUsuarioCadastro_UsuarioSemPermissao() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/Cadastro/Usuario")
                    .sessionAttr("user", usuarioComum)) // Simula um usuário comum
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Verifica redirecionamento
            .andExpect(MockMvcResultMatchers.redirectedUrl("/Erro/Permissao")); // URL de redirecionamento
}

    // Teste: GET /Cadastro/Usuario - Usuário com permissão
    @Test
    public void testUsuarioCadastro_UsuarioAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/Cadastro/Usuario")
                        .sessionAttr("user", usuarioAdmin))
                .andExpect(status().isOk()) // Espera status 200 OK
                .andExpect(view().name("cadastroUsuario"));
    }

    // Teste: POST /Cadastro/Usuario - Usuário sem permissão
    @Test
    public void testUsuarioCadastroPost_UsuarioSemPermissao() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/Cadastro/Usuario")
                        .sessionAttr("user", usuarioComum)
                        .param("username", "novoUsuario")
                        .param("password", "senha")
                        .param("admin", "false"))
                .andExpect(status().isForbidden()); // Espera status 403
    }

    // Teste: POST /Cadastro/Usuario - Usuário com permissão
    @Test
    public void testUsuarioCadastroPost_UsuarioAdmin() throws Exception {
        Mockito.doNothing().when(usuarioService).salvarUsuarioComCarrinho(Mockito.any(), Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.post("/Cadastro/Usuario")
                        .sessionAttr("user", usuarioAdmin)
                        .param("username", "novoUsuario")
                        .param("password", "senha")
                        .param("admin", "true"))
                .andExpect(status().isCreated()) // Espera status 201 Created
                .andExpect(jsonPath("$.username").value("novoUsuario"));
    }
}
