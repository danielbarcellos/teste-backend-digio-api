package br.com.digio.api.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.digio.api.service.PurchaseService;
import br.com.digio.api.service.model.Purchase;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Value("${app.compras.url}")
	private String comprasUrl;

	private RestTemplate template;

	public PurchaseServiceImpl() {
		this.template = new RestTemplate();
	}
	
	public PurchaseServiceImpl(RestTemplate template, String comprasUrl) {
		this.template = template;
		this.comprasUrl = comprasUrl;
	}
	
	private final List<Purchase> CACHE = Collections.synchronizedList(new ArrayList<>());
	
	@PostConstruct
	public void onPostConstruct() throws RestClientException, URISyntaxException {
		if(CACHE.isEmpty()) {
			ResponseEntity<Purchase[]> entity = this.template.getForEntity(new URI(this.comprasUrl), Purchase[].class);
			CACHE.addAll(Arrays.asList(entity.getBody()));
		}
	}

	@Override
	public List<Purchase> getAll() {
		try {
			this.onPostConstruct();
			return CACHE;
		} catch (RestClientException | URISyntaxException e) {
			log.error(e.getMessage(), e);
		}

		return Collections.emptyList();
	}
	
	public List<Purchase> getCACHE() {
		return CACHE;
	}
}
