package com.example.ControleFarmacia.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ControleFarmacia.Models.Carrinho;
import com.example.ControleFarmacia.Models.CarrinhoItem;
import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Repositories.CarrinhoItemRepo;
import com.example.ControleFarmacia.Repositories.UsuarioRepo;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoItemRepo carrinhoItemRepo;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private UsuarioRepo usuarioRepo;

    // Adicionar produto ao carrinho do usuário
    public CarrinhoItem adicionarProduto(int usuarioId, int produtoId, int quantidade) {
        // Verifica se o usuário existe
        Optional<Usuario> optionalUsuario = usuarioRepo.findById(usuarioId);
        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
    
        Usuario usuario = optionalUsuario.get();
    
        // Verifica se o produto existe
        Optional<Produto> optionalProduto = produtoService.findById(produtoId);
        if (optionalProduto.isEmpty()) {
            throw new RuntimeException("Produto não encontrado");
        }
    
        Produto produto = optionalProduto.get();
    
        // Criar ou obter o carrinho do usuário
        Carrinho carrinho = usuario.getCarrinhos();
    
        // Verifica se o produto já está no carrinho
        Optional<CarrinhoItem> optionalCarrinhoItem = carrinho.getItens()
            .stream()
            .filter(item -> item.getProduto().getId() == produtoId)
            .findFirst();
    
        if (optionalCarrinhoItem.isPresent()) {
            // Incrementa a quantidade se o item já existir
            CarrinhoItem carrinhoItemExistente = optionalCarrinhoItem.get();
            carrinhoItemExistente.setQuantidade(carrinhoItemExistente.getQuantidade() + quantidade);
            return carrinhoItemRepo.save(carrinhoItemExistente);
        } else {
            // Cria um novo item se o produto não estiver no carrinho
            CarrinhoItem novoCarrinhoItem = new CarrinhoItem();
            novoCarrinhoItem.setProduto(produto);
            novoCarrinhoItem.setQuantidade(quantidade);
            novoCarrinhoItem.setCarrinho(carrinho);
    
            return carrinhoItemRepo.save(novoCarrinhoItem);
        }
    }

    // Listar itens do carrinho de um usuário
    public List<CarrinhoItem> listarItensDoCarrinho(int usuarioId) {
        Optional<Usuario> optionalUsuario = usuarioRepo.findById(usuarioId);
        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = optionalUsuario.get();
        Carrinho carrinho = usuario.getCarrinhos() == null ? null : usuario.getCarrinhos(); // Aqui também considera-se o carrinho ativo

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
        Carrinho carrinho = usuario.getCarrinhos() == null ? null : usuario.getCarrinhos(); // Aqui também considera-se o carrinho ativo

        if (carrinho == null) {
            throw new RuntimeException("Carrinho não encontrado para o usuário");
        }

        carrinhoItemRepo.deleteAll();
    }
}
