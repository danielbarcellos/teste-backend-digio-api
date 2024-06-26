package br.com.digio.api.service;

import java.util.List;

import br.com.digio.api.dto.CompraDTO;
import br.com.digio.api.dto.CompraUnicaDTO;
import br.com.digio.api.exception.ApiException;

public interface ApiService {

	List<CompraDTO> getCompras() throws ApiException;

	CompraUnicaDTO getMaiorCompraPorAno(Integer ano) throws ApiException;

	List<CompraUnicaDTO> getClientesFieis() throws ApiException;

	CompraUnicaDTO getRecomendacaoClientePorTipo(String cpf, String tipo) throws ApiException;
}
