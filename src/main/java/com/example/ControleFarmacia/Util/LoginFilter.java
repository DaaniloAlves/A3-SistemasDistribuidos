package com.example.ControleFarmacia.Util;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends HttpFilter implements Filter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Verifica se o usuário está autenticado (exemplo usando a sessão HTTP)
        if (request.getSession().getAttribute("user") == null) {
            // Redireciona para a página de login se não estiver autenticado
            response.sendRedirect("/Login");
        } else {
            // Continua com a requisição normalmente
            chain.doFilter(request, response);
        }
    }
}
