package br.ufg.inf.backend.spring.controller;

import java.util.List;
import java.util.Optional;

import br.ufg.inf.backend.spring.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import br.ufg.inf.backend.spring.model.Produto;

@Controller
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

//    @GetMapping("/produtos/{id}")
//    public String listarProduto(@PathVariable Long id, Model model) {
//
//        Optional<Produto> produto = produtoService.findById(id);
//
//        if (produto.isPresent()) {
//            model.addAttribute("produto", produto.get());
//            return "produto-detalhes";
//        } else {
//            model.addAttribute("erro", "Produto não encontrado, conferir id: " + id);
//        }
//        return "produtos";
//    }


    @GetMapping("/produtos")  //Endpoint
    public String listarProdutos(Model model) {  //método

        List<Produto> produtos = produtoService.listAll();
        model.addAttribute("produtos", produtos);  // "produtos" no Thymeleaf '${produtos}'
        return "produtos";                                     // Retorna o nome da view (produtos.html) que deve ser renderizada

    }


    // Endpoint para mostrar o formulário de adicionar produto
    @GetMapping("/produtos/adicionar")
    public String mostrarFormularioAdicionarProduto() {

        return "adicionar-produto"; // Retorna o nome da view (adicionar-produto.html) que deve ser renderizada
    }


    // Endpoint para adicionar um novo produto
    @PostMapping("/produtos")
    public String adicionarProduto(@RequestParam String nome, @RequestParam Double preco, RedirectAttributes redirectAttributes) {

        if (nome == null || nome.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "O nome do produto não pode ser vazio.");
            return "redirect:/produtos/adicionar";
        }

        if (preco == null || preco <= 0) {
            redirectAttributes.addFlashAttribute("erro", "O preço do produto deve ser maior que zero.");
            return "redirect:/produtos/adicionar";
        }

        try {
            produtoService.add(nome, preco);
            redirectAttributes.addFlashAttribute("sucesso", "Produto adicionado com sucesso!");

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("erro", "Erro ao adicionar o produto: " + e.getMessage());

        }

        //return "produtos";
        // Retorna o nome da view (produtos.html) que deve ser renderizada
        // exibir uma página sem alterar a URL no navegador.


        return "redirect:/produtos";
        //uso ideal após operações de alteração de estado no servidor (como criar, atualizar ou deletar recursos) para evitar
        //reenvio de formulário ao atualizar a página e garantir que o navegador carregue a URL atualizada.


    }


    @GetMapping("/produtos/editar") //abre a página de edição
    public String mostrarFormularioEditarProduto(@RequestParam("id") Long id, Model model)
    //Extrai o parâmetro id da URL da requisição.
    {
        Optional<Produto> produto = produtoService.findById(id);
        model.addAttribute("produto", produto.get()); //adiciona produto ao modelo
        return "editar-produto";
    }

    @PostMapping("/produtos/editar")
    public String alterarProduto(@RequestParam Long id, @RequestParam String nome, @RequestParam double preco, RedirectAttributes redirectAttributes) {
        try {
            Produto produto = new Produto(id, nome, preco);
            produtoService.update(id, nome, preco);
            redirectAttributes.addAttribute("sucesso", "Produto alterado com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao alterar o produto: " + e.getMessage());

        }
        return "redirect:/produtos";
    }


    @PostMapping("/produtos/deletar")
    public String deletarProduto(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            produtoService.delete(id);
            redirectAttributes.addFlashAttribute("sucesso", "Produto deletado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao remover produto: " + e.getMessage());
        }
        return "redirect:/produtos";
    }


//    @GetMapping("produtos/deletar")
//    public String deletarProduto(@RequestParam("id")Long id, RedirectAttributes redirectAttributes) {
//        try {
//            produtoService.delete(id);
//            redirectAttributes.addAttribute("sucesso", "Produto deletado com sucesso!");
//
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("erro", "Erro ao remover produto: " + e.getMessage());
//        }
//
//        return "redirect:/produtos";
//    }


//    @DeleteMapping("/produtos/deletar")
//    public String excluirProduto(@PathVariable Long id, Model model) {
//        try{
//            produtoService.delete(id);
//        }catch (Exception e) {
//            model.addAttribute("erro", "Erro ao deletar o produto: " + e.getMessage());
//            model.addAttribute("produtos", produtoService.listAll());
//
//
//        }
//        return "produtos"; // Retorna a view que será renderizada
//
//    }
}




