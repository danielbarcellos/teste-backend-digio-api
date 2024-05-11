package br.com.digio.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digio.api.dto.ClienteDTO;
import br.com.digio.api.dto.CompraDTO;
import br.com.digio.api.dto.ProdutoDTO;
import br.com.digio.api.exception.ApiException;
import br.com.digio.api.model.Compra;
import br.com.digio.api.model.Produto;
import br.com.digio.api.model.Purchase;
import br.com.digio.api.service.ApiService;
import br.com.digio.api.service.CompraService;
import br.com.digio.api.service.ProdutoService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ApiServiceImpl implements ApiService {

	@Autowired
	private ProdutoService produdoService;

	@Autowired
	private CompraService compraService;
	
	@Override
	public CompraDTO getMaiorCompraPorAno(Integer ano) throws ApiException {
		List<Purchase> all = this.compraService.getAll();
		List<CompraDTO> data = new ArrayList<>();

		for (Purchase p : all) {
			List<Compra> compras = p.getCompras();
			
			for (Compra compra : compras) {
				Optional<Produto> optional = this.produdoService.getProdutoById(compra.getCodigo());
				
				if(optional.isPresent() && ano.compareTo(optional.get().getAno_compra()) == 0) {
					CompraDTO cliente = CompraDTO.builder()
						.cpf(p.getCpf())
						.nome(p.getNome())
					.build();
					
					Produto produto = optional.get();
					
					if(!data.contains(cliente)) {
						data.add(cliente);
					}
					
					List<ProdutoDTO> produtos = cliente.getProdutos();
					
					produtos.add(ProdutoDTO.builder()
							.codigo(compra.getCodigo())
							.precoTotal(produto.getPreco().multiply(BigDecimal.valueOf(compra.getQuantidade())))
							.quantidade(compra.getQuantidade())
							.safra(produto.getSafra())
							.tipoVinho(produto.getTipo_vinho())
							.build());
					
					cliente.setProdutos(produtos);
				}
			}
		}
		
		return data.stream().sorted((o1, o2) -> o2.getValorTotal().compareTo(o1.getValorTotal())).findFirst().orElseThrow();
	}

	public List<CompraDTO> getCompras() throws ApiException {

		List<Purchase> all = this.compraService.getAll();

		return all.stream()
				.map(c -> CompraDTO.builder()
						.cpf(c.getCpf())
						.nome(c.getNome())
						.produtos(this.getProdutos(c.getCompras()))
					.build())
				.sorted((o1, o2) -> o1.getValorTotal().compareTo(o2.getValorTotal()))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<ClienteDTO> getClientesFieis() throws ApiException {

		List<Purchase> all = this.compraService.getAll();

		List<ClienteDTO> data = new ArrayList<>();
		
		for (Purchase purchase : all) {
			List<Compra> compras = purchase.getCompras();
			
			for (Compra compra : compras) {
				Produto p = this.produdoService.getProdutoById(compra.getCodigo()).get();
				data.add(ClienteDTO.builder()
						.cpf(purchase.getCpf())
						.nome(purchase.getNome())
						.quantidade(compra.getQuantidade())
						.valorTotal(p.getPreco().multiply(BigDecimal.valueOf(compra.getQuantidade())))
						.produto(ProdutoDTO.builder()
									.codigo(p.getCodigo())
									.precoTotal(p.getPreco())
									.quantidade(compra.getQuantidade())
									.safra(p.getSafra())
									.tipoVinho(p.getTipo_vinho())
								.build())
					.build());
			}
		}
		
		return data.stream()
				.sorted((o1, o2) -> o2.getQuantidade().compareTo(o1.getQuantidade()))
				.sorted((o1, o2) -> o2.getValorTotal().compareTo(o1.getValorTotal()))
				.collect(Collectors.toList())
			.subList(0, 3);
	}
	
	@Override
	public ProdutoDTO getRecomendacaoClientePorTipo(String cpf, String tipo) throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<ProdutoDTO> getProdutos(List<Compra> compras) {
		return compras.stream().map(this::getProdutosPorCompra).collect(Collectors.toList());
	}
	
	private ProdutoDTO getProdutosPorCompra(Compra c) {
		Optional<Produto> optional = this.produdoService.getProdutoById(c.getCodigo());
		
		return getProductFromOptional(c, optional);
	}
	
	private ProdutoDTO getProductFromOptional(Compra c, Optional<Produto> optional) {
		if (optional.isPresent()) {
			return ProdutoDTO.builder()
					.codigo(optional.get().getCodigo())
					.precoTotal(optional.get().getPreco().multiply(BigDecimal.valueOf(c.getQuantidade())))
					.quantidade(c.getQuantidade()).safra(optional.get().getSafra())
					.tipoVinho(optional.get().getTipo_vinho())
					.build();
		}
		
		return null;
	}
}
