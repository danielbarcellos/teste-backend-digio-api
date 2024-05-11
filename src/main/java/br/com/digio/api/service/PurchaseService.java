package br.com.digio.api.service;

import java.util.List;

import br.com.digio.api.service.model.Purchase;

public interface PurchaseService {

	List<Purchase> getAll();

	List<Purchase> getAllByAno(Integer ano);

	List<Purchase> getAllByProdutoId(Integer id);
}
