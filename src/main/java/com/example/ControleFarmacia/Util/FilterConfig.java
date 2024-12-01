package com.example.ControleFarmacia.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ControleFarmacia.Models.Carrinho;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Repositories.UsuarioRepo;
import com.example.ControleFarmacia.Services.UsuarioService;

@Configuration
public class FilterConfig {
@Autowired
    UsuarioService usuarioService;

    @Bean
    public CommandLineRunner dataLoader(UsuarioRepo usuarioRepo) {
        return args -> {
            if (usuarioRepo.count() == 0) {
                Usuario usuario = new Usuario();
                usuario.setUsername("admin");
                usuario.setPassword("admin");
                usuario.setRole("ADMIN");
                usuario.setAtivo(true);
                Carrinho carrinho = new Carrinho();
                carrinho.setUsuario(usuario);
                usuario.setCarrinho(carrinho);
                usuarioService.salvarUsuarioComCarrinho(usuario, carrinho);
            }
        };
    }
    
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginFilter());
    
        // Define as URLs protegidas pelo filtro
        registrationBean.addUrlPatterns("/Home/*", "/Cadastro/*", "/Carrinho/*", "/Gerenciar/*");
        
        return registrationBean;
    }
}