package br.com.digio.api.service;

import java.util.Optional;

import br.com.digio.api.model.Produto;

public interface ProdutoService {

	Optional<Produto> getProdutoById(Integer produtoId);

	Optional<Produto> getAllByCodigoAndAno(Integer produtoId, Integer ano);
}
