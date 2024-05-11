package br.com.digio.api.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.digio.api.service.impl.ProductServiceImpl;
import br.com.digio.api.service.model.Product;

public class ProductServiceImplTest {
	
	final String produtoUrl = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json";

	private ProductServiceImpl productService;
	private RestTemplate restTemplate;

	@Before
	public void onBefore() {
		restTemplate = mock(RestTemplate.class);
		productService = new ProductServiceImpl(restTemplate, produtoUrl);
	}
	
	@Test
	public void testOnPostConstruct() throws RestClientException, URISyntaxException {
		// Arrange
		ResponseEntity<Product[]> responseEntity = new ResponseEntity<>(new Product[0], HttpStatus.OK);
		when(restTemplate.getForEntity(new URI(produtoUrl), Product[].class)).thenReturn(responseEntity);

		// Act
		productService.onPostConstruct();

		// Assert
		assertEquals(Collections.emptyList(), productService.getProdutoCache());
	}

	@Test
	public void testGetProdutoById_CacheNotEmpty() {
		// Arrange
		Product product1 = new Product(1, "Product 1", null, null, null, null);
		Product product2 = new Product(2, "Product 2", null, null, null, null);
		productService.getProdutoCache().addAll(Arrays.asList(product1, product2));

		// Act
		Optional<Product> result = productService.getProdutoById(1);

		// Assert
		assertTrue(result.isPresent());
		assertEquals(product1, result.get());
	}

	@Test
	public void testGetProdutoById_CacheEmpty_SuccessfulRequest() throws RestClientException, URISyntaxException {
		// Arrange
		Product product1 = new Product(1, "Product 1", null, null, null, null);
		Product product2 = new Product(2, "Product 2", null, null, null, null);
		ResponseEntity<Product[]> responseEntity = new ResponseEntity<>(new Product[] { product1, product2 },
				HttpStatus.OK);
		when(restTemplate.getForEntity(new URI(produtoUrl), Product[].class)).thenReturn(responseEntity);

		// Act
		Optional<Product> result = productService.getProdutoById(1);

		// Assert
		assertTrue(result.isPresent());
		assertEquals(product1, result.get());
		assertEquals(Arrays.asList(product1, product2), productService.getProdutoCache());
	}

	@Test
	public void testGetProdutoById_CacheEmpty_FailedRequest() throws RestClientException, URISyntaxException {
		// Arrange
		when(restTemplate.getForEntity(new URI(produtoUrl), Product[].class))
				.thenThrow(new RestClientException("Failed to retrieve products"));

		// Act
		Optional<Product> result = productService.getProdutoById(1);

		// Assert
		assertFalse(result.isPresent());
		assertEquals(Collections.emptyList(), productService.getProdutoCache());
	}
}
