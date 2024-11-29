package com.example.ControleFarmacia.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ControleFarmacia.Models.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(@Param("username") String login);
    @Query("SELECT u FROM Usuario u ORDER BY u.id DESC")
    Optional<Usuario> findLastUser();
    @Query("SELECT u.password FROM Usuario u WHERE u.username = :username")
    String findPasswordByUsername(@Param("username") String username);
}
