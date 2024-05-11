package br.com.digio.api.service;

import java.util.List;

import br.com.digio.api.dto.ClienteDTO;
import br.com.digio.api.dto.CompraDTO;
import br.com.digio.api.dto.ProdutoDTO;
import br.com.digio.api.exception.ApiException;

public interface ApiService {

	List<CompraDTO> getCompras() throws ApiException;

	CompraDTO getMaiorCompraPorAno(Integer ano) throws ApiException;
//
	List<ClienteDTO> getClientesFieis() throws ApiException;
//
	ProdutoDTO getRecomendacaoClientePorTipo(String cpf, String tipo) throws ApiException;

}
