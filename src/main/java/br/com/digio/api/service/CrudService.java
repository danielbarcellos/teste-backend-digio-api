package br.com.digio.api.service;

import br.com.digio.api.exception.ApiException;

public interface CrudService<Q, S> {
	
	java.util.List<S> getAll() throws ApiException;

	S save(Q request) throws ApiException;

	S put(Long id, Q request) throws ApiException;

	void delete(Long id) throws ApiException;
}
