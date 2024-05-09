package br.com.digio.api.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.digio.api.controller.ApiResource;
import br.com.digio.api.exception.ApiException;
import br.com.digio.api.service.ApiService;

@RestController
@RequestMapping(ApiResource.REQUEST_MAP) 	
public class ApiResourceImpl implements ApiResource {

	@Autowired
	private ApiService service;

	@Override
	public ResponseEntity<?> getCompras() throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> getMaiorCompraPorAno(Integer ano) throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> getClientesFieis(Integer ano) throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> getRecomendacaoClientePorTipo(String cliente, String tipo) throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
