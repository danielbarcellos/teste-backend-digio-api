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
	
	RestTemplate template = new RestTemplate();

	private static final List<Product> PRODUTO_CACHE = Collections.synchronizedList(new ArrayList<>());
	
	@PostConstruct
	private void onPostConstruct() throws RestClientException, URISyntaxException {
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
	
	@Override
	public Optional<Product> getAllByCodigoAndAno(Integer produtoId, Integer ano) {
		try {
			if(PRODUTO_CACHE.isEmpty()) {
				ResponseEntity<Product[]> entity = this.template.getForEntity(new URI(this.produtoUrl), Product[].class);
				PRODUTO_CACHE.addAll(Arrays.asList(entity.getBody())) ;
			}
			
			
			return PRODUTO_CACHE.stream()
						.filter(p -> ano.compareTo(p.getAno_compra()) == 0 && produtoId.compareTo(p.getCodigo()) == 0)
					.findFirst();
			
		} catch (RestClientException | URISyntaxException e) {
			log.warn(e.getMessage());
		}
		return Optional.empty();
	}
}