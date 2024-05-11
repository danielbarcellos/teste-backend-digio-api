package br.com.digio.api.controller;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.digio.api.controller.impl.ApiResourceImpl;
import br.com.digio.api.dto.CompraDTO;
import br.com.digio.api.dto.CompraUnicaDTO;
import br.com.digio.api.exception.ApiException;
import br.com.digio.api.service.ApiService;

public class ApiResourceImplTest {

	@Mock
	private ApiService service;

	@InjectMocks
	private ApiResourceImpl apiResource;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetCompras() throws ApiException {
		// Arrange
		List<CompraDTO> compras = new ArrayList<>();
		compras.add(new CompraDTO("NOME 1", "1234", BigDecimal.valueOf(10.0), Collections.emptyList()));
		compras.add(new CompraDTO("NOME 2", "4321", BigDecimal.valueOf(20.0), Collections.emptyList()));
		when(service.getCompras()).thenReturn(compras);

		// Act
		ResponseEntity<List<CompraDTO>> response = apiResource.getCompras();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(compras, response.getBody());
		verify(service, times(1)).getCompras();
	}

	@Test
	public void testGetMaiorCompraPorAno() throws ApiException {
		// Arrange
		Integer ano = 2021;
		CompraUnicaDTO compra = new CompraUnicaDTO("NOME 1", "1234", BigDecimal.valueOf(10.0), 1, 1L, "tipo", BigDecimal.ZERO, null, null);
		when(service.getMaiorCompraPorAno(ano)).thenReturn(compra);

		// Act
		ResponseEntity<CompraUnicaDTO> response = apiResource.getMaiorCompraPorAno(ano);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(compra, response.getBody());
		verify(service, times(1)).getMaiorCompraPorAno(ano);
	}

	@Test
	public void testGetClientesFieis() throws ApiException {
		// Arrange
		List<CompraUnicaDTO> clientesFieis = new ArrayList<>();
		clientesFieis.add(new CompraUnicaDTO("NOME 1", "1234", BigDecimal.valueOf(10.0), 1, 1L, "tipo", BigDecimal.ZERO, null, null));
		clientesFieis.add(new CompraUnicaDTO("NOME 2", "4321", BigDecimal.valueOf(20.0), 1, 1L, "tipo", BigDecimal.ZERO, null, null));
		when(service.getClientesFieis()).thenReturn(clientesFieis);

		// Act
		ResponseEntity<List<CompraUnicaDTO>> response = apiResource.getClientesFieis();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(clientesFieis, response.getBody());
		verify(service, times(1)).getClientesFieis();
	}

	@Test
	public void testGetRecomendacaoClientePorTipo() throws ApiException {
		// Arrange
		String cpf = "123456789";
		String tipo = "Product";
		CompraUnicaDTO recomendacao = new CompraUnicaDTO("NOME 2", "4321", BigDecimal.valueOf(20.0), 1, 1L, "tipo", BigDecimal.ZERO, null, null);
		when(service.getRecomendacaoClientePorTipo(cpf, tipo)).thenReturn(recomendacao);

		// Act
		ResponseEntity<CompraUnicaDTO> response = apiResource.getRecomendacaoClientePorTipo(cpf, tipo);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(recomendacao, response.getBody());
		verify(service, times(1)).getRecomendacaoClientePorTipo(cpf, tipo);
	}
}
