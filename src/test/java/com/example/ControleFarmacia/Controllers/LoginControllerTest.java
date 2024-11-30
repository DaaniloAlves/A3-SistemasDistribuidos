package com.example.ControleFarmacia.Controllers;

import java.util.Optional;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Services.UsuarioService;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @InjectMocks
    private LoginController loginController;

    private Usuario usuarioMock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        usuarioMock = new Usuario();
        usuarioMock.setId(1);
        usuarioMock.setPassword("password");
        usuarioMock.setUsername("admin");
        usuarioMock.setRole("ADMIN");
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/Login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginIndex"));
    }

    @Test
    public void testLogar_Sucesso() throws Exception {
        when(usuarioService.buscarPorLoginESenha("admin", "password")).thenReturn(Optional.of(usuarioMock));

        mockMvc.perform(MockMvcRequestBuilders.post("/Login")
                        .param("login", "admin")
                        .param("senha", "password")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection()) // Redirecionamento em caso de sucesso
                .andExpect(redirectedUrl("/Home"));

        verify(usuarioService, times(1)).buscarPorLoginESenha("admin", "password");
    }

    @Test
    public void testLogar_Falha() throws Exception {
        when(usuarioService.buscarPorLoginESenha("admin", "wrongpassword")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.post("/Login")
                        .param("login", "admin")
                        .param("senha", "wrongpassword")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk()) // Recarrega a tela de login
                .andExpect(view().name("loginIndex"));

        verify(usuarioService, times(1)).buscarPorLoginESenha("admin", "wrongpassword");
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/Login/Logout"))
                .andExpect(status().is3xxRedirection()) // Redirecionamento após logout
                .andExpect(redirectedUrl("/Login"));
    }
}
