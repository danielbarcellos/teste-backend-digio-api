package br.com.digio.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.digio.api.dto.CarrinhoDTO;
import br.com.digio.api.exception.ApiException;

public interface ApiResource {

	static final String REQUEST_MAP = "/v1";
	
	@GetMapping("/compras")
	ResponseEntity<java.util.List<CarrinhoDTO>> getCompras() throws ApiException;

//	@GetMapping("/maior-compra/{ano}")
//	ResponseEntity<Compra> getMaiorCompraPorAno(@PathVariable Integer ano) throws ApiException;
//
//	@GetMapping("/clientes-fieis")
//	ResponseEntity<java.util.List<CarrinhoDTO>> getClientesFieis() throws ApiException;
//
//	@GetMapping("/recomentacao/{cpf}/{tipo}")
//	ResponseEntity<CarrinhoDTO> getRecomendacaoClientePorTipo(@PathVariable String cpf, @PathVariable String tipo) throws ApiException;
}
