package com.example.ControleFarmacia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ControleFarmacia.Models.CarrinhoItem;

public interface CarrinhoItemRepo extends JpaRepository<CarrinhoItem, Integer> {
    List<CarrinhoItem> findByCarrinhoId(int carrinhoId);
    void deleteAllByCarrinhoIdAndQuantidade(int carrinhoId, int quantidade);
}
