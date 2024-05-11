package br.com.digio.api.service;

import java.util.List;

import br.com.digio.api.model.Purchase;

public interface CompraService {

	List<Purchase> getAll();

	List<Purchase> getAllByAno(Integer ano);

	List<Purchase> getAllByProdutoId(Integer id);
}
