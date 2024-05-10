package br.com.digio.api.service;

import java.util.List;

import br.com.digio.api.dto.CompraDTO;
import br.com.digio.api.exception.ApiException;

public interface ApiService {

	List<CompraDTO> getCompras() throws ApiException;

//	Compra getMaiorCompraPorAno(Integer ano) throws ApiException;
//
//	List<CarrinhoDTO> getClientesFieis() throws ApiException;
//
//	CarrinhoDTO getRecomendacaoClientePorTipo(String cpf, String tipo) throws ApiException;

}
