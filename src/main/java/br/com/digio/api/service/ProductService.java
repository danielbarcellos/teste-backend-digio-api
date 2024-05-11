package br.com.digio.api.service;

import java.util.Optional;

import br.com.digio.api.service.model.Product;

public interface ProductService {

	Optional<Product> getProdutoById(Integer produtoId);
}
