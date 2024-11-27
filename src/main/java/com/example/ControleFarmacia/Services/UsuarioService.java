package com.example.ControleFarmacia.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Repositories.UsuarioRepo;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    UsuarioRepo repo;

    public boolean usuarioExiste(Optional<Usuario> usuario) {
        Optional<Usuario> usuarioVerificado = repo.findByUsername(usuario.get().getUsername());
        if (usuarioVerificado.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repo.findByUsername(username); // Busque o usuário pelo nome de usuário
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        System.out.println("Usuário encontrado: " + usuario.get().getUsername());
        
        return new Usuario(usuario.get().getUsername(), usuario.get().getPassword());
    }

    public Optional<Usuario> findById(int id) {
        return repo.findById(id);
    }
}
