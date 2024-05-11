package br.com.digio.api.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.digio.api.service.ProductService;
import br.com.digio.api.service.model.Product;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

	@Value("${app.produtos.url}")
	private String produtoUrl;
	
	private RestTemplate template;

	public ProductServiceImpl() {
		this.template = new RestTemplate();
	}
	
	public ProductServiceImpl(RestTemplate template, String produtoUrl) {
		this.template = template;
		this.produtoUrl = produtoUrl;
	}

	private final List<Product> PRODUTO_CACHE = Collections.synchronizedList(new ArrayList<>());
	
	@PostConstruct
	public void onPostConstruct() throws RestClientException, URISyntaxException {
		if(PRODUTO_CACHE.isEmpty()) {
			ResponseEntity<Product[]> entity = this.template.getForEntity(new URI(this.produtoUrl), Product[].class);
			PRODUTO_CACHE.addAll(Arrays.asList(entity.getBody())) ;
		}
	}
	
	@Override
	public Optional<Product> getProdutoById(Integer produtoId) {
		try {
			if(PRODUTO_CACHE.isEmpty()) {
				ResponseEntity<Product[]> entity = this.template.getForEntity(new URI(this.produtoUrl), Product[].class);
				PRODUTO_CACHE.addAll(Arrays.asList(entity.getBody())) ;
			}
		
			
			return PRODUTO_CACHE.stream()
					.filter(p -> produtoId.compareTo(p.getCodigo()) == 0)
				.findFirst();
			
		} catch (RestClientException | URISyntaxException e) {
			log.warn(e.getMessage());
		}
		
		return Optional.empty();
	}
	
	public List<Product> getProdutoCache() {
		return PRODUTO_CACHE;
	}
}
