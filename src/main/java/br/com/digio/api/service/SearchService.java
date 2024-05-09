package br.com.digio.api.service;

import br.com.digio.api.exception.ApiException;

public interface SearchService<Out> {

	Out getById(Long id) throws ApiException;
}
