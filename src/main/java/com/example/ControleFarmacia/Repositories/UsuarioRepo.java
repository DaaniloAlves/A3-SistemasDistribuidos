package com.example.ControleFarmacia.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ControleFarmacia.Models.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsernameAndPassword(String username, String password);
}
