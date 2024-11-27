package com.example.ControleFarmacia.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ControleFarmacia.Models.Carrinho;

public interface CarrinhoRepo extends JpaRepository<Carrinho, Integer> {
    
}
