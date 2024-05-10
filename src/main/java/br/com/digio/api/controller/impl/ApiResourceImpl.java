package br.com.digio.api.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.digio.api.controller.ApiResource;
import br.com.digio.api.dto.CompraDTO;
import br.com.digio.api.exception.ApiException;
import br.com.digio.api.service.ApiService;

@RestController
@RequestMapping(ApiResource.REQUEST_MAP) 	
public class ApiResourceImpl implements ApiResource {

	@Autowired
	private ApiService service;

	@Override
	public ResponseEntity<List<CompraDTO>> getCompras() throws ApiException {
		return new ResponseEntity<>(this.service.getCompras(), HttpStatus.OK);
	}
//	@Override
//	public ResponseEntity<Compra> getMaiorCompraPorAno(Integer ano) throws ApiException {
//		return new ResponseEntity<>(this.service.getMaiorCompraPorAno(ano), HttpStatus.OK);
//	}
//
//	@Override
//	public ResponseEntity<List<CarrinhoDTO>> getClientesFieis() throws ApiException {
//		return new ResponseEntity<>(this.service.getClientesFieis(), HttpStatus.OK);
//	}
//
//	@Override
//	public ResponseEntity<CarrinhoDTO> getRecomendacaoClientePorTipo(String cpf, String tipo) throws ApiException {
//		return new ResponseEntity<>(this.service.getRecomendacaoClientePorTipo(cpf, tipo), HttpStatus.OK);
//	}

	
}
