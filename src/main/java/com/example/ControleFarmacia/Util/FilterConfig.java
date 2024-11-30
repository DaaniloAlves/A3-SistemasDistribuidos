package com.example.ControleFarmacia.Util;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginFilter());
    
        // Define as URLs protegidas pelo filtro
        registrationBean.addUrlPatterns("/Home/*", "/Cadastro/*", "/Carrinho/*");
        
        return registrationBean;
    }
}