package com.example.ControleFarmacia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ControleFarmacia.models.Carrinho;
import com.example.ControleFarmacia.models.CarrinhoItem;
import com.example.ControleFarmacia.models.Produto;
import com.example.ControleFarmacia.models.Usuario;
import com.example.ControleFarmacia.repositories.CarrinhoItemRepo;
import com.example.ControleFarmacia.repositories.CarrinhoRepo;
import com.example.ControleFarmacia.repositories.UsuarioRepo;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepo carrinhoRepo;

    @Autowired
    private CarrinhoItemRepo carrinhoItemRepo;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private UsuarioRepo usuarioRepo;

    // Criar um carrinho para um usuário
    public Carrinho criarCarrinho(int usuarioId) {
        Optional<Usuario> optionalUsuario = usuarioRepo.findById(usuarioId);
        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = optionalUsuario.get();

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario); // Relacionando o carrinho com o usuário

        return carrinhoRepo.save(carrinho);
    }

    // Adicionar produto ao carrinho do usuário
    public CarrinhoItem adicionarProduto(int usuarioId, int produtoId, int quantidade) {
        // Verifica se o usuário existe
        Optional<Usuario> optionalUsuario = usuarioRepo.findById(usuarioId);
        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = optionalUsuario.get();

        // Verifica se o produto existe
        Optional<Produto> produto = produtoService.findById(produtoId);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado");
        }

        // Criar ou obter o carrinho do usuário
        Carrinho carrinho = usuario.getCarrinhos().isEmpty() ? criarCarrinho(usuarioId) : usuario.getCarrinhos().get(0); // Aqui assume-se que o usuário tem apenas um carrinho ativo, mas isso pode ser ajustado
        // Cria o item do carrinho
        CarrinhoItem carrinhoItem = new CarrinhoItem();
        carrinhoItem.setProduto(produto.get());
        carrinhoItem.setQuantidade(quantidade);
        carrinhoItem.setCarrinho(carrinho);

        // Salva o item no carrinho
        return carrinhoItemRepo.save(carrinhoItem);
    }

    // Listar itens do carrinho de um usuário
    public List<CarrinhoItem> listarItensDoCarrinho(int usuarioId) {
        Optional<Usuario> optionalUsuario = usuarioRepo.findById(usuarioId);
        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = optionalUsuario.get();
        Carrinho carrinho = usuario.getCarrinhos().isEmpty() ? null : usuario.getCarrinhos().get(0); // Aqui também considera-se o carrinho ativo

        if (carrinho == null) {
            throw new RuntimeException("Carrinho não encontrado para o usuário");
        }

        return carrinhoItemRepo.findAll();
    }

    // Remover item do carrinho de um usuário
    public void removerProdutoDoCarrinho(int usuarioId, int itemId) {
        CarrinhoItem item = carrinhoItemRepo.findById(itemId).orElseThrow(() -> new RuntimeException("Item não encontrado"));
        carrinhoItemRepo.delete(item);
    }

    // Limpar o carrinho de um usuário
    public void limparCarrinho(int usuarioId) {
        Optional<Usuario> optionalUsuario = usuarioRepo.findById(usuarioId);
        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = optionalUsuario.get();
        Carrinho carrinho = usuario.getCarrinhos().isEmpty() ? null : usuario.getCarrinhos().get(0); // Aqui também considera-se o carrinho ativo

        if (carrinho == null) {
            throw new RuntimeException("Carrinho não encontrado para o usuário");
        }

        carrinhoItemRepo.deleteAll();
    }
}
