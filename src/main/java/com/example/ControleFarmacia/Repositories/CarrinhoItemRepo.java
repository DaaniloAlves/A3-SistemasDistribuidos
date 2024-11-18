package com.example.ControleFarmacia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ControleFarmacia.models.CarrinhoItem;

public interface CarrinhoItemRepo extends JpaRepository<CarrinhoItem, Integer> {
    
}
