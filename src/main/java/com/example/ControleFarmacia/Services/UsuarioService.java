package com.example.ControleFarmacia.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Repositories.UsuarioRepo;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepo repo;

    public boolean usuarioExiste(Optional<Usuario> usuario) {
        Optional<Usuario> usuarioVerificado = repo.findByLogin(usuario.get().getLogin());
        if (usuarioVerificado.isEmpty()) {
            return false;
        }
        return true;
    }

    public Optional<Usuario> findById(int id) {
        return repo.findById(id);
    }
}
