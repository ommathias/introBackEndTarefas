package br.ufg.inf.backend.repository;

import br.ufg.inf.backend.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}