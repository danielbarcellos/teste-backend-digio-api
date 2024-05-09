package br.com.digio.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.digio.api.exception.ApiException;

public interface ApiResource {

	static final String REQUEST_MAP = "/v1";
	
	@GetMapping("/compras")
	ResponseEntity<?> getCompras() throws ApiException;

	@GetMapping("/maior-compra/{ano}")
	ResponseEntity<?> getMaiorCompraPorAno(@PathVariable Integer ano) throws ApiException;

	@GetMapping("/clientes-fieis")
	ResponseEntity<?> getClientesFieis(@PathVariable Integer ano) throws ApiException;

	@GetMapping("/recomentacao/{cliente}/{tipo}")
	ResponseEntity<?> getRecomendacaoClientePorTipo(@PathVariable String cliente, @PathVariable String tipo) throws ApiException;
}
