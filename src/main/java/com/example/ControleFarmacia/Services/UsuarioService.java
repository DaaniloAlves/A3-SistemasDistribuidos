package com.example.ControleFarmacia.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ControleFarmacia.DTOs.UsuarioDTO;
import com.example.ControleFarmacia.Models.Carrinho;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Repositories.CarrinhoRepo;
import com.example.ControleFarmacia.Repositories.UsuarioRepo;

@Service
public class UsuarioService {
    private final UsuarioRepo usuarioRepo;
    private final CarrinhoRepo carrinhoRepo;

    @Autowired
    public UsuarioService(UsuarioRepo usuarioRepo, CarrinhoRepo carrinhoRepo) {
        this.usuarioRepo = usuarioRepo;
        this.carrinhoRepo = carrinhoRepo;
    }

    public Usuario buscarUltimoUsuario() {
        return usuarioRepo.findLastUser()
                .orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado"));
    }

    public boolean usuarioExiste(Optional<UsuarioDTO> usuario) {
        Optional<Usuario> usuarioVerificado = usuarioRepo.findByUsername(usuario.get().getUsername());
        if (usuarioVerificado.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean verificarLogin(Optional<UsuarioDTO> usuario) {
            if (usuarioExiste(usuario) && usuario.get().getPassword().equals(usuarioRepo.findPasswordByUsername(usuario.get().getUsername()))) {
                return true;
            }
            return false;
        }
    

public Usuario criarUsuario(Usuario usuario) {
    // Cria o usuário
    Usuario savedUsuario = usuarioRepo.save(usuario);
    return new Usuario(savedUsuario.getUsername(), savedUsuario.getPassword(), savedUsuario.getRole());
}

public void salvarUsuarioComCarrinho(Usuario usuario, Carrinho carrinho) {
    carrinhoRepo.save(carrinho); // Salva o carrinho primeiro
    usuario.setCarrinho(carrinho); // Associa o carrinho ao usuário
    usuarioRepo.save(usuario); // Salva o usuário novamente
}
}
