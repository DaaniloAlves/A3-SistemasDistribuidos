package com.example.ControleFarmacia.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ControleFarmacia.Models.Carrinho;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Repositories.CarrinhoRepo;
import com.example.ControleFarmacia.Repositories.UsuarioRepo;
import com.example.ControleFarmacia.Util.HashUtil;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepo usuarioRepo;
    private final CarrinhoRepo carrinhoRepo;

    @Autowired
    public UsuarioService(UsuarioRepo usuarioRepo, CarrinhoRepo carrinhoRepo) {
        this.usuarioRepo = usuarioRepo;
        this.carrinhoRepo = carrinhoRepo;
    }
        public Optional<Usuario> buscarPorLoginESenha(String login, String senha) {
            String senhaHash = HashUtil.gerarHash(senha);
            Optional<Usuario> usuarioOptional = usuarioRepo.findByUsernameAndPassword(login, senhaHash);
            if (usuarioOptional.isPresent()) {
                return usuarioOptional; // Retorna o usuário completo se encontrado
            }
            return Optional.empty();
        }

        @Transactional
        public void salvarUsuarioComCarrinho(Usuario usuario, Carrinho carrinho) {
            // Encriptando a senha
            Usuario usuarioComSenhaHash = usuario;
            usuarioComSenhaHash.setPassword(HashUtil.gerarHash(usuario.getPassword()));
            // Salva o usuário sem o carrinho ainda associado
            Usuario usuarioSalvo = usuarioRepo.save(usuarioComSenhaHash);
        
            // Atualiza o carrinho com o usuário associado
            carrinho.setUsuario(usuarioSalvo);
            Carrinho carrinhoSalvo = carrinhoRepo.save(carrinho);
        
            // Atualiza o usuário com o carrinho salvo
            usuarioSalvo.setCarrinho(carrinhoSalvo);
            usuarioRepo.save(usuarioSalvo);
        }
}
