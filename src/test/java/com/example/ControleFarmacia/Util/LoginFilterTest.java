package com.example.ControleFarmacia.Util;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginFilterTest {

    private LoginFilter loginFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private HttpSession session;  // Adicionado mock para HttpSession

    @BeforeEach
    public void setUp() {
        // Criar mocks para o HttpServletRequest, HttpServletResponse, FilterChain e HttpSession
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        session = mock(HttpSession.class); // Mock para a sessão

        // Configura o HttpServletRequest para retornar o mock de HttpSession
        when(request.getSession()).thenReturn(session);

        loginFilter = new LoginFilter();  // Instanciar o LoginFilter
    }

    @Test
    public void testDoFilter_UserNotAuthenticated_RedirectToLogin() throws IOException, ServletException {
        // Simula que o atributo "user" na sessão é null (usuário não autenticado)
        when(session.getAttribute("user")).thenReturn(null);

        // Chama o método doFilter
        loginFilter.doFilter(request, response, chain);

        // Verifica se o redirecionamento para "/Login" foi chamado
        verify(response).sendRedirect("/Login");
        
        // Garantir que a cadeia de filtros não foi chamada (já que o filtro bloqueia a requisição)
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    public void testDoFilter_UserAuthenticated_AllowRequestToContinue() throws IOException, ServletException {
        // Simula que o atributo "user" na sessão não é null (usuário autenticado)
        when(session.getAttribute("user")).thenReturn(new Object());

        // Chama o método doFilter
        loginFilter.doFilter(request, response, chain);

        // Verifica se a cadeia de filtros foi chamada, permitindo a requisição continuar
        verify(chain).doFilter(request, response);

        // Garantir que o redirecionamento não foi chamado (o usuário está autenticado)
        verify(response, never()).sendRedirect("/Login");
    }
}
