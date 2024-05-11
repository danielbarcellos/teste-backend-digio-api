package br.com.digio.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.digio.api.dto.ClienteDTO;
import br.com.digio.api.dto.CompraDTO;
import br.com.digio.api.dto.ProdutoDTO;
import br.com.digio.api.exception.ApiException;

public interface ApiResource {

	static final String REQUEST_MAP = "/v1";
	
	@GetMapping("/compras")
	ResponseEntity<java.util.List<CompraDTO>> getCompras() throws ApiException;
//
	@GetMapping("/maior-compra/{ano}")
	ResponseEntity<CompraDTO> getMaiorCompraPorAno(@PathVariable Integer ano) throws ApiException;
//
	@GetMapping("/clientes-fieis")
	ResponseEntity<java.util.List<ClienteDTO>> getClientesFieis() throws ApiException;

	@GetMapping("/recomentacao/{cpf}/{tipo}")
	ResponseEntity<ProdutoDTO> getRecomendacaoClientePorTipo(@PathVariable String cpf, @PathVariable String tipo) throws ApiException;
}
