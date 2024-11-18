package com.example.ControleFarmacia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ControleFarmacia.models.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
    
}
