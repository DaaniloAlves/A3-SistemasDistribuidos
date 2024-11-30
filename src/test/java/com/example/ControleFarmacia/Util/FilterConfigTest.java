package com.example.ControleFarmacia.Util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import jakarta.servlet.Filter;

public class FilterConfigTest {

    @Test
    public void testLoginFilterRegistration() {
        // Criar um contexto de aplicação mockado
        ApplicationContext context = new AnnotationConfigApplicationContext(FilterConfig.class);

        // Verificar se o FilterRegistrationBean foi registrado no contexto
        FilterRegistrationBean<LoginFilter> filterRegistrationBean = context.getBean(FilterRegistrationBean.class);

        // Verificar se o filtro foi registrado corretamente
        assertNotNull(filterRegistrationBean, "O filtro não foi registrado corretamente.");
        
        // Verificar se o filtro correto foi adicionado
        Filter filter = filterRegistrationBean.getFilter();
        assertTrue(filter instanceof LoginFilter, "O filtro registrado não é do tipo esperado.");
        
        // Verificar se as URLs estão sendo protegidas corretamente
        assertTrue(filterRegistrationBean.getUrlPatterns().contains("/Home/*"), "URL /Home/* não foi adicionada.");
        assertTrue(filterRegistrationBean.getUrlPatterns().contains("/Carrinho/*"), "URL /Carrinho/* não foi adicionada.");
        assertTrue(filterRegistrationBean.getUrlPatterns().contains("/Cadastro/*"), "URL /Cadastro/* não foi adicionada.");
    }
}
