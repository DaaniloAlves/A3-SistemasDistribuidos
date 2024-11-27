package com.example.ControleFarmacia.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ControleFarmacia.Models.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
    @Query(value="SELECT * FROM Usuario WHERE login = :login", nativeQuery = true)
    Optional<Usuario> findByLogin(@Param("login") String login);
}
