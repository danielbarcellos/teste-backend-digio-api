package br.com.digio.api.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.digio.api.dto.CarrinhoDTO;
import br.com.digio.api.dto.ClienteDTO;
import br.com.digio.api.exception.ApiException;
import br.com.digio.api.model.Aquisicao;
import br.com.digio.api.model.Compra;
import br.com.digio.api.model.Produto;
import br.com.digio.api.service.ApiService;
import br.com.digio.api.service.ProdudoService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ApiServiceImpl implements ApiService {
	
	@Value("${app.produtos.url}")
	private String produtosUrl;
	
	@Value("${app.compras.url}")
	private String comprasUrl;
	
	@Autowired
	private ProdudoService produdoService;

	private final RestTemplate template = new RestTemplate();
	
	
	/**
	 * Retornar uma lista das compras ordenadas de forma crescente por valor,
	 * deve conter o nome dos clientes, cpf dos clientes, dado dos produtos, 
	 * quantidade das compras e valores totais de cada compra.
	 */
	public List<CarrinhoDTO> getCompras() throws ApiException {
		try {
			ResponseEntity<Compra[]> entity = this.template.getForEntity(new URI(this.comprasUrl), Compra[].class);
			
			List<Compra> carrinho = Arrays.asList(entity.getBody());
			return carrinho
				.stream()
				.map(c -> CarrinhoDTO.builder()
							.produtos(toProdutos(c.getCompras()))
							.cliente(ClienteDTO.builder()
										.cpf(c.getCpf())
										.nome(c.getNome())
									.build())
						.build())
				.sorted((o1, o2) -> o1.getValorTotal().compareTo(o2.getValorTotal()))
				.collect(Collectors.toList())
			;
		} catch (RestClientException | URISyntaxException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}


	private List<Produto> toProdutos(List<Aquisicao> compras) {
		return compras
				.stream()
				.map(this::toProduto)
				.collect(Collectors.toList())
				;
	}


	private Produto toProduto(Aquisicao a) {
		Optional<Produto> oProduto = this.produdoService.getProdutoById(a.getCodigo());
		if(oProduto.isPresent()) {
			return oProduto.get();
		}
		return null;
	}

//	@Override
//	public Compra getMaiorCompraPorAno(Integer ano) throws ApiException {
//		try {
//			ResponseEntity<Compra> entity = this.template.getForEntity(new URI(this.produtosUrl), Compra.class);
//			
////			entity.getBody().get
//		} catch (RestClientException | URISyntaxException e) {
//			throw new ApiException(e.getMessage(), e);
//		}
//		
//		return null;
//	}
//
//	/**
//	 * Retornar o Top 3 clientes mais fieis, clientes que possuem mais compras recorrentes com maiores valores.
//	 */
//	@Override
//	public List<CarrinhoDTO> getClientesFieis() throws ApiException {
//		try {
//			ResponseEntity<CarrinhoDTO[]> entity = this.template.getForEntity(new URI(this.comprasUrl), CarrinhoDTO[].class);
//			
//			return Arrays.asList(entity.getBody())
//				.stream()
//				.collect(Collectors.toList());
//			
//		} catch (RestClientException | URISyntaxException e) {
//			throw new ApiException(e.getMessage(), e);
//		}
//	}
//
//	@Override
//	public CarrinhoDTO getRecomendacaoClientePorTipo(String cpf, String tipo) throws ApiException {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	private Compra getProduto(Compra compra) {
//		Optional<Produto> optional = this.produdoService.getProdutoById(compra.getCodigo());
//		if(optional.isPresent())
//			compra.setProduto(optional.get());
//		
//		return compra;
//	}
	
}
