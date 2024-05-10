package br.com.digio.api.service;

import java.util.Optional;

import br.com.digio.api.model.Produto;

public interface ProdudoService {

	Optional<Produto> getProdutoById(Integer produtoId);
}
