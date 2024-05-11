package br.com.digio.api.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.digio.api.service.impl.PurchaseServiceImpl;
import br.com.digio.api.service.model.Purchase;

public class PurchaseServiceImplTest {

	final String produtoUrl = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json";

	private PurchaseServiceImpl purchaseService;
	private RestTemplate restTemplate;

	@Before
	public void onBefore() {
		restTemplate = mock(RestTemplate.class);
		purchaseService = new PurchaseServiceImpl(restTemplate, produtoUrl);
	}
	
	public void setUp() {
	}

	@Test
	public void testOnPostConstruct() throws RestClientException, URISyntaxException {
		// Arrange
		ResponseEntity<Purchase[]> responseEntity = new ResponseEntity<>(new Purchase[0], HttpStatus.OK);
		when(restTemplate.getForEntity(new URI(produtoUrl), Purchase[].class)).thenReturn(responseEntity);

		// Act
		purchaseService.onPostConstruct();

		// Assert
		assertEquals(Collections.emptyList(), purchaseService.getCACHE());
	}
}
