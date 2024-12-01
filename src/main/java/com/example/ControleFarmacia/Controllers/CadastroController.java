package com.example.ControleFarmacia.Controllers;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.ControleFarmacia.Models.Carrinho;
import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Models.Usuario;
import com.example.ControleFarmacia.Services.ProdutoService;
import com.example.ControleFarmacia.Services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Cadastro")
public class CadastroController {
    private static final String imagemCaminho = "src/main/resources/static/images/";

    @Autowired
    ProdutoService produtoService;
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/Produto")
    public ModelAndView produtoCadastro(HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario)request.getSession().getAttribute("user");
        if (usuarioLogado == null) {
            return new ModelAndView("erroPermissao");
        }
        // Verifica se o usuário tem a role ADMIN
        if (!"ADMIN".equalsIgnoreCase(usuarioLogado.getRole())) {
        return new ModelAndView("erroPermissao");
    }
    return new ModelAndView("cadastroProduto");
    }
    @PostMapping("/Produto")
    public ModelAndView produtoCadastro(@RequestParam("name") String nome, @RequestParam("desc") String desc, @RequestParam("qtd") int qtd, @RequestParam("price") float preco, @RequestParam("img") MultipartFile img, HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario)request.getSession().getAttribute("user");
        // Verifica se o usuário tem a role ADMIN
        if (!"ADMIN".equalsIgnoreCase(usuarioLogado.getRole())) {
        return new ModelAndView("erroPermissao");
    }
        Produto produto = new Produto();
        produto.setDescricao(desc);
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setQtd(qtd);


        File imagemDir = new File(imagemCaminho);
        if (!imagemDir.exists()) {
            imagemDir.mkdirs(); // Cria o diretório se ele não existir
        }
        Path caminhoArquivo = Paths.get(imagemCaminho + (nome) + ".jpg");
        try {
            img.transferTo(caminhoArquivo);
            System.out.println("Salvamento da imagem deu certo");
        } catch (Exception e) {
            System.out.println("Salvamento da imagem deu errado");
        }
        produtoService.add(produto);
        return new ModelAndView("redirect:/Gerenciar/Produtos");
    }

    @GetMapping("/Usuario")
    public ModelAndView cadastrarUsuario(HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario)request.getSession().getAttribute("user");
        // Verifica se o usuário tem a role ADMIN
        if (!"ADMIN".equalsIgnoreCase(usuarioLogado.getRole())) {
        return new ModelAndView("erroPermissao");
    }
        return new ModelAndView("cadastroUsuario");
    }

    @PostMapping("/Usuario")
    public ModelAndView cadastrarUsuario(@RequestParam String username, @RequestParam String password, @RequestParam String role, HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario)request.getSession().getAttribute("user");
        // Verifica se o usuário tem a role ADMIN
        if (!"ADMIN".equalsIgnoreCase(usuarioLogado.getRole())) {
            return new ModelAndView("erroPermissao");
    }
        Usuario usuario = new Usuario(username, password, role, true);
        // Cria e associa um carrinho ao usuário
        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario);
        usuario.setCarrinho(carrinho);
        usuarioService.salvarUsuarioComCarrinho(usuario, carrinho);
        return new ModelAndView("redirect:/Gerenciar/Usuarios");
    }
}
