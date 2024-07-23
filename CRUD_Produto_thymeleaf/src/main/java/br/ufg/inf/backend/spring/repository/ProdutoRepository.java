package br.ufg.inf.backend.spring.repository;

import br.ufg.inf.backend.spring.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

