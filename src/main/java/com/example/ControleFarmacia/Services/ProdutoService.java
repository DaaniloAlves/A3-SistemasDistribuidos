package com.example.ControleFarmacia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ControleFarmacia.models.Produto;
import com.example.ControleFarmacia.repositories.ProdutoRepo;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepo repo;

    public Produto add(Produto produto) {
        return repo.save(produto);
    }
    public Optional<Produto> findById(int id) {
        return repo.findById(id);
    }
    public Iterable<Produto> findAll() {
        return repo.findAll();
    }

}
