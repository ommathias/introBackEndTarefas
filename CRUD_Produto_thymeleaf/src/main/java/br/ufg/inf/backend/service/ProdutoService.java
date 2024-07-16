package br.ufg.inf.backend.service;

import br.ufg.inf.backend.model.Produto;
import br.ufg.inf.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {


    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }}