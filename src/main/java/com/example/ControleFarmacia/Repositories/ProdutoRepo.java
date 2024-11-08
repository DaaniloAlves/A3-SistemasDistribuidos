package com.example.ControleFarmacia.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.ControleFarmacia.models.Produto;

public interface ProdutoRepo extends CrudRepository<Produto, Integer> {
    
}
