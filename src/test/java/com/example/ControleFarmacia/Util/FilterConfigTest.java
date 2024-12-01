package com.example.ControleFarmacia.Util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import com.example.ControleFarmacia.Models.Carrinho;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Repositories.UsuarioRepo;
import com.example.ControleFarmacia.Services.UsuarioService;

@SpringBootTest
public class FilterConfigTest {

    @Mock
    private UsuarioRepo usuarioRepo;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private FilterConfig filterConfig;

    @Test
    public void testDataLoader_WhenNoUserExists_ShouldCreateUserWithCarrinho() {
        // Mockando o comportamento do repositorio para retornar 0 usuários
        when(usuarioRepo.count()).thenReturn(0L);

        CommandLineRunner dataLoader = filterConfig.dataLoader(usuarioRepo);

        try {
            dataLoader.run(); // Executa o método dataLoader
        } catch (Exception e) {
            e.printStackTrace(); // Captura a exceção, caso ocorra
        }

        // Verificar se o serviço foi chamado para salvar o usuário e o carrinho
        verify(usuarioService).salvarUsuarioComCarrinho(Mockito.any(Usuario.class), Mockito.any(Carrinho.class));
    }

    @Test
    public void testLoginFilterRegistration() {
        FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterConfig().loginFilter();

        // Verifica se o filtro foi registrado
        assertTrue(filterRegistrationBean.getUrlPatterns().contains("/Home/*"));
        assertTrue(filterRegistrationBean.getUrlPatterns().contains("/Cadastro/*"));
        assertTrue(filterRegistrationBean.getUrlPatterns().contains("/Carrinho/*"));
        assertTrue(filterRegistrationBean.getUrlPatterns().contains("/Gerenciar/*"));
    }
}
