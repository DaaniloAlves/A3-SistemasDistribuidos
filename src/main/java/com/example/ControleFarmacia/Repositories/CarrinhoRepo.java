package com.example.ControleFarmacia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ControleFarmacia.models.Carrinho;

public interface CarrinhoRepo extends JpaRepository<Carrinho, Integer> {
    
}
