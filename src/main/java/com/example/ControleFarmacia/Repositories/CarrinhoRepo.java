package com.example.ControleFarmacia.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.ControleFarmacia.Models.Produto;

public interface CarrinhoRepo extends CrudRepository<Produto, Integer> {
    
}
