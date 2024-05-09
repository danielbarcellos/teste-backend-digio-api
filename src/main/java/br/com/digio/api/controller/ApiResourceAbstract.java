package br.com.digio.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.digio.api.exception.ApiException;
import br.com.digio.api.service.CrudService;

public abstract class ApiResourceAbstract<Q,S> {

	protected CrudService<Q, S> service;
	
	public ApiResourceAbstract(CrudService<Q, S> service) {
		this.service = service;
	}
	
	public ResponseEntity<List<S>> getAll() throws ApiException {
		return new ResponseEntity<List<S>>(this.service.getAll(), HttpStatus.OK);
	}

	public ResponseEntity<S> post(Q request) throws ApiException {
		return new ResponseEntity<S>(this.service.save(request), HttpStatus.CREATED);
	}

	public ResponseEntity<S> put(Long id, Q request) throws ApiException {
		return new ResponseEntity<S>(this.service.put(id, request), HttpStatus.OK);
	}

	public ResponseEntity<Void> delete(Long id) throws ApiException {
		this.service.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
