package br.ufg.inf.backend.spring.service;

import br.ufg.inf.backend.spring.model.Produto;
import br.ufg.inf.backend.spring.repository.ProdutoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    private List<Produto> produtos = new ArrayList<Produto>(Arrays.asList(
            new Produto(1L, "Notebook", 2000.00)
            , new Produto(2L, "Smartphone", 1000.00))
    );

    public List<Produto> listAll() {
        return produtoRepository.findAll();
    }

    //checar esse retorno caseo ID não exista
    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
        // e se não encontrar?

    }

    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void delete(Long id) {

        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {
            produtoRepository.deleteById(id);

        }else {
            throw new RuntimeException("Produto não existe, conferir ID: " + id);
        }
    }

    public Produto update(Long id, String nome, Double preco) {
        if (id == null)
            throw new RuntimeException("ID do produto não deve ser nulo.");
        if (!produtoRepository.existsById(id))
            throw new RuntimeException("Produto não encontrado");

        Produto produto = new Produto(id, nome, preco);
        return produtoRepository.save(produto);

    }

    public Produto add(String nome, double preco) {

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio.");
        }
        if (preco <= 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
        }
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setPreco(preco);
        return produtoRepository.save(produto);
    }


    //  inicializar o banco de dados com produtos
    @PostConstruct
    public void init() {
        if (produtoRepository.count() == 0) {
            produtoRepository.save(new Produto(null, "Notebook", 2000.00));
            produtoRepository.save(new Produto(null, "Smartphone", 1000.00));
        }
    }


}
