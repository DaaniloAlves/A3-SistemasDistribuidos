package com.example.ControleFarmacia.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ControleFarmacia.Models.CarrinhoItem;

public interface CarrinhoItemRepo extends JpaRepository<CarrinhoItem, Integer> {
    
}
