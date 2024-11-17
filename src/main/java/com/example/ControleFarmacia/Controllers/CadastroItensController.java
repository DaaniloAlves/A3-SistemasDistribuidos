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

import com.example.ControleFarmacia.Models.Produto;
import com.example.ControleFarmacia.Services.ProdutoService;

@RestController
@RequestMapping("/Cadastro")
public class CadastroItensController {

    private static final String imagemCaminho = "src/main/resources/templates/imagens/";

    @Autowired
    ProdutoService service;

    @GetMapping()
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("cadastroIndex");
        return mv;
    }
    @PostMapping("/Cadastrado")
    public String cadastrado(@RequestParam("name") String nome, @RequestParam("desc") String desc, @RequestParam("qtd") int qtd, @RequestParam("price") float preco, @RequestParam("img") MultipartFile img) {
        Produto produto = new Produto();
        produto.setDescricao(desc);
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setQtd(qtd);


        File imagemDir = new File(imagemCaminho);
        Path caminhoArquivo = Paths.get(imagemCaminho + (nome) + ".jpg");
        try {
            img.transferTo(caminhoArquivo);
            System.out.println("certo");
        } catch (Exception e) {
            System.out.println("errado");
        }
        service.add(produto);
        return "produto cadastrado com sucesso";
    }
}
