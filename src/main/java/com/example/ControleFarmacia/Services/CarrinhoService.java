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
import com.example.ControleFarmacia.Repositories.CarrinhoRepo;
import com.example.ControleFarmacia.Repositories.ProdutoRepo;
import com.example.ControleFarmacia.Repositories.UsuarioRepo;

import jakarta.transaction.Transactional;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoItemRepo carrinhoItemRepo;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepo produtoRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private CarrinhoRepo carrinhoRepo;

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
    
        // Verifica se há estoque suficiente
        if (produto.getQuantidade() < quantidade) {
            throw new RuntimeException("Estoque insuficiente para este produto");
        }
    
        // Criar ou obter o carrinho do usuário
        Carrinho carrinho = usuario.getCarrinho();
    
        // Verifica se o produto já está no carrinho
        Optional<CarrinhoItem> optionalCarrinhoItem = carrinho.getItens()
                .stream()
                .filter(item -> item.getProduto().getId() == produtoId)
                .findFirst();
    
        if (optionalCarrinhoItem.isPresent()) {
            // Incrementa a quantidade se o item já existir
            CarrinhoItem carrinhoItemExistente = optionalCarrinhoItem.get();
            carrinhoItemExistente.setQuantidade(carrinhoItemExistente.getQuantidade() + quantidade);
    
            // Atualiza o estoque
            produto.setQuantidade(produto.getQuantidade() - quantidade);
            produtoRepo.save(produto);
    
            return carrinhoItemRepo.save(carrinhoItemExistente);
        } else {
            // Cria um novo item se o produto não estiver no carrinho
            CarrinhoItem novoCarrinhoItem = new CarrinhoItem();
            novoCarrinhoItem.setProduto(produto);
            novoCarrinhoItem.setQuantidade(quantidade);
            novoCarrinhoItem.setCarrinho(carrinho);
            novoCarrinhoItem.setPrecoUnitario(produto.getPreco());
    
            // Atualiza o estoque
            produto.setQuantidade(produto.getQuantidade() - quantidade);
            produtoRepo.save(produto);
    
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
        Carrinho carrinho = usuario.getCarrinho();
        if (carrinho == null) {
            throw new RuntimeException("Carrinho não encontrado para o usuário");
        }

        // Retorna apenas os itens associados ao carrinho do usuário
        return carrinhoItemRepo.findByCarrinhoId(carrinho.getId());
    }

    // Remover item do carrinho de um usuário
    public void removerProdutoDoCarrinho(int usuarioId, int itemId) {
        CarrinhoItem item = carrinhoItemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    
        Produto produto = item.getProduto();
    
        // Atualiza o estoque
        produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
        produtoRepo.save(produto);
    
        // Remove o item do carrinho
        carrinhoItemRepo.delete(item);
    }
    

    public void removerUmaUnidadeDoProduto(int usuarioId, int itemId) {
        CarrinhoItem item = carrinhoItemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    
        Produto produto = item.getProduto();
    
        if (item.getQuantidade() > 1) {
            // Reduz a quantidade no carrinho
            item.setQuantidade(item.getQuantidade() - 1);
            carrinhoItemRepo.save(item);
    
            // Atualiza o estoque
            produto.setQuantidade(produto.getQuantidade() + 1);
            produtoRepo.save(produto);
        } else {
            // Remove o item do carrinho e atualiza o estoque
            produto.setQuantidade(produto.getQuantidade() + 1);
            produtoRepo.save(produto);
            carrinhoItemRepo.delete(item);
        }
    }
    

    // Limpar o carrinho de um usuário
    @Transactional
    public void limparCarrinho(int usuarioId) {
        Optional<Usuario> optionalUsuario = usuarioRepo.findById(usuarioId);
        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
    
        Usuario usuario = optionalUsuario.get();
        Carrinho carrinho = usuario.getCarrinho();
    
        if (carrinho == null) {
            throw new RuntimeException("Carrinho não encontrado para o usuário");
        }
    
        // Iterar sobre os itens do carrinho e remover os itens com quantidade 0
        List<CarrinhoItem> itensCarrinho = carrinho.getItens(); // Carregar itens
        for (CarrinhoItem item : itensCarrinho) {
            Produto produto = item.getProduto();
    
            // Adicionar de volta a quantidade de cada produto ao estoque
            produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
            produtoRepo.save(produto); // Atualizar produto no banco
    
            // Excluir o item do carrinho se a quantidade for 0
            if (item.getQuantidade() == 0) {
                carrinhoItemRepo.delete(item); // Excluir o item diretamente
            } else {
                // Caso contrário, zerar a quantidade do item
                item.setQuantidade(0);
                carrinhoItemRepo.save(item); // Salvar item com quantidade zerada
            }
        }
    
        // Excluir itens com quantidade zero diretamente do banco
        carrinhoItemRepo.deleteAllByCarrinhoIdAndQuantidade(carrinho.getId(), 0);
    
        // Limpar a lista de itens do carrinho após a exclusão
        carrinho.getItens().clear();
        carrinhoRepo.save(carrinho); // Atualizar o carrinho no banco
    }
    
    

    
    
}
