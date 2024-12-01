package com.example.ControleFarmacia.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Repositories.ProdutoRepo;
import com.example.ControleFarmacia.Repositories.UsuarioRepo;
import com.example.ControleFarmacia.Services.ProdutoService;
import com.example.ControleFarmacia.Services.UsuarioService;
import com.example.ControleFarmacia.Util.HashUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/Gerenciar")
public class GerenciadorController {

    @Autowired
    ProdutoService produtoService;
    @Autowired
    ProdutoRepo produtoRepo;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioRepo usuarioRepo;

    
    @GetMapping("/Produtos")
    public ModelAndView telaProdutos() {
        ModelAndView mv = new ModelAndView("gerenciadorProdutos");
        Iterable<Produto> produtosIterable = produtoService.findAll();
        List<Produto> produtos = new ArrayList<>();
        produtosIterable.forEach(produtos::add);
        mv.addObject("produtos", produtos);
        return mv;
    }
    @GetMapping("/Usuarios")
    public ModelAndView telaUsuarios() {
        ModelAndView mv = new ModelAndView("gerenciadorUsuarios");
        Iterable<Usuario> usuariosIterable = usuarioService.findAll();
        List<Usuario> usuarios = new ArrayList<>();
        usuariosIterable.forEach(usuarios::add);
        mv.addObject("usuarios", usuarios);
        return mv;
    }
    @GetMapping("/editar-produto/{id}")
    public ModelAndView editarProduto(@PathVariable int id) {
        Optional<Produto> produto = produtoService.findById(id);
        ModelAndView mv = new ModelAndView("editarProduto");
        mv.addObject("produto", produto.get());
        return mv;
    }

    // Endpoint para processar o formulário de edição
    @PostMapping("/editar-produto")
    public ModelAndView atualizarProduto(@RequestParam("id") int id,
                                   @RequestParam("nome") String nome,
                                   @RequestParam("descricao") String descricao,
                                   @RequestParam("preco") float preco,
                                   @RequestParam("quantidade") Integer quantidade) {
                                    Optional<Produto> produto = produtoService.findById(id);
        produto.get().setNome(nome);
        produto.get().setDescricao(descricao);
        produto.get().setPreco(preco);
        produto.get().setQuantidade(quantidade);


        produtoRepo.save(produto.get());
        return new ModelAndView("redirect:/Gerenciar/Produtos"); // Redireciona para a lista de produtos
    }
    @GetMapping("/editar-usuario/{id}")
    public ModelAndView editarUsuario(@PathVariable int id) {
        Optional<Usuario> usuario = usuarioRepo.findById(id);
        ModelAndView mv = new ModelAndView("editarUsuario");
        mv.addObject("usuario", usuario.get());
        return mv;
    }

    // Endpoint para processar o formulário de edição
    @PostMapping("/editar-usuario")
    public ModelAndView atualizarUsuario(@RequestParam("id") int id,
                                   @RequestParam("username") String username,
                                   @RequestParam(value = "password", required = false) String password,
                                   @RequestParam("role") String role) {
        Optional<Usuario> usuario = usuarioRepo.findById(id);
        usuario.get().setUsername(username);

        // Atualiza a senha se foi fornecida
        if (password != null && !password.isEmpty()) {
            usuario.get().setPassword(HashUtil.gerarHash(password)); // Criptografa a senha
        }

        usuario.get().setRole(role);  // Atualiza a role do usuário
        usuarioRepo.save(usuario.get());
        return new ModelAndView("redirect:/Gerenciar/Usuarios"); // Redireciona para a lista de usuários
    }

    // Método para excluir um produto
@GetMapping("/deletar-produto/{id}")
public ModelAndView deletarProduto(@PathVariable int id) {
    Optional<Produto> produto = produtoRepo.findById(id);
    if (produto.isPresent()) {
        produtoRepo.delete(produto.get()); // Exclui o produto
    }
    return new ModelAndView("redirect:/Gerenciar/Produtos"); // Redireciona para a lista de produtos
}
@GetMapping("/desativar-usuario/{id}")
public ModelAndView desativarUsuario(@PathVariable int id, HttpServletRequest request) {
    Optional<Usuario> usuarioOptional = usuarioRepo.findById(id);
    if (usuarioOptional.isPresent()) {
        Usuario usuario = usuarioOptional.get();
        usuario.setAtivo(false); // Atualiza o atributo ativo para false
        usuarioRepo.save(usuario); // Salva o usuário com a alteração
    }
    
    // Verifica se o usuário está logado na sessão
    Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("user");
    if (usuarioLogado != null && usuarioLogado.getId() == id) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/Login/Logout");
    }
    
    return new ModelAndView("redirect:/Gerenciar/Usuarios"); // Redireciona para a lista de usuários
}

@GetMapping("/ativar-usuario/{id}")
public ModelAndView ativarUsuario(@PathVariable int id, HttpServletRequest request) {
    Optional<Usuario> usuarioOptional = usuarioRepo.findById(id);
    if (usuarioOptional.isPresent()) {
        Usuario usuario = usuarioOptional.get();
        usuario.setAtivo(true); // Atualiza o atributo ativo para false
        usuarioRepo.save(usuario); // Salva o usuário com a alteração
    }
    return new ModelAndView("redirect:/Gerenciar/Usuarios"); // Redireciona para a lista de produtos
}


}
