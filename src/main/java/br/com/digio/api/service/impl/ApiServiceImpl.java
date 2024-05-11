package br.com.digio.api.service.impl;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digio.api.dto.CompraDTO;
import br.com.digio.api.dto.CompraUnicaDTO;
import br.com.digio.api.dto.ProdutoDTO;
import br.com.digio.api.exception.ApiException;
import br.com.digio.api.service.ApiService;
import br.com.digio.api.service.ProductService;
import br.com.digio.api.service.PurchaseService;
import br.com.digio.api.service.model.Buying;
import br.com.digio.api.service.model.Product;
import br.com.digio.api.service.model.Purchase;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ApiServiceImpl implements ApiService {

	private ProductService productService;

	private PurchaseService purchaseService;
	
	@Autowired
	public ApiServiceImpl(ProductService productService, PurchaseService purchaseService) {
		this.productService = productService;
		this.purchaseService = purchaseService;
	}
	
	@Override
	public CompraUnicaDTO getMaiorCompraPorAno(Integer ano) throws ApiException {
		List<Purchase> all = this.purchaseService.getAll();
		
		final List<CompraUnicaDTO> data = new ArrayList<>();

		all
			.stream()
			.forEach(purchase -> data
				.addAll(purchase.getCompras()
					.stream()
					.map(this::convertBuyingIntoProduct)
					.filter(p -> ano.compareTo(p.getAno_compra()) == 0)
					.map(p -> CompraUnicaDTO.builder()
								.cpf(purchase.getCpf())
								.nome(purchase.getNome())
								.codigo(p.getCodigo())
								.precoUnitario(p.getPreco())
								.safra(p.getSafra())
								.tipoVinho(p.getTipo_vinho())
								.quantidade(p.getQuantidadeComprada())
								.anoCompra(p.getAno_compra())
							.build())
					.collect(Collectors.toList())))
			
		;
		
		return data.stream().sorted((o1, o2) -> o2.getValorTotal().compareTo(o1.getValorTotal())).findFirst().orElseThrow();
	}
	
	@Override
	public CompraUnicaDTO getRecomendacaoClientePorTipo(String cpf, String tipo) throws ApiException {
		
		List<Purchase> all = this.purchaseService.getAll();
		
		final List<CompraUnicaDTO> data = new ArrayList<>();

		all
			.stream()
			.filter(f -> equalsIgnoreCase(cpf, f.getCpf()))
			.forEach(purchase -> data
				.addAll(purchase.getCompras()
					.stream()
					.map(this::convertBuyingIntoProduct)
					.filter(p -> equalsIgnoreCase(tipo, p.getTipo_vinho()))
					.map(p -> CompraUnicaDTO.builder()
								.cpf(purchase.getCpf())
								.nome(purchase.getNome())
								.codigo(p.getCodigo())
								.precoUnitario(p.getPreco())
								.safra(p.getSafra())
								.tipoVinho(p.getTipo_vinho())
								.quantidade(p.getQuantidadeComprada())
								.anoCompra(p.getAno_compra())
							.build())
					.collect(Collectors.toList())))
			
		;
		
		return data.stream().sorted((o1, o2) -> o2.getQuantidade().compareTo(o1.getQuantidade())).findFirst().orElseThrow();
	}

	public List<CompraDTO> getCompras() throws ApiException {

		List<Purchase> all = this.purchaseService.getAll();

		return all.stream()
				.map(c -> CompraDTO.builder()
						.cpf(c.getCpf())
						.nome(c.getNome())
						.produtos(this.toProductsFromBuyings(c.getCompras()))
					.build())
				.sorted((o1, o2) -> o1.getValorTotal().compareTo(o2.getValorTotal()))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<CompraUnicaDTO> getClientesFieis() throws ApiException {

		List<Purchase> all = this.purchaseService.getAll();

		final List<CompraUnicaDTO> data = new ArrayList<>();
		
		all
			.forEach(purchase -> data.addAll(purchase.getCompras()
					.stream()
					.map(this::convertBuyingIntoProduct)
					.map(p -> CompraUnicaDTO.builder()
							.cpf(purchase.getCpf())
							.nome(purchase.getNome())
							.codigo(p.getCodigo())
							.precoUnitario(p.getPreco())
							.safra(p.getSafra())
							.tipoVinho(p.getTipo_vinho())
							.quantidade(p.getQuantidadeComprada())
							.anoCompra(p.getAno_compra())
						.build())
					.collect(Collectors.toList())))
		;
		
		return data.stream()
				.sorted((o1, o2) -> o2.getQuantidade().compareTo(o1.getQuantidade()))
				.sorted((o1, o2) -> o2.getValorTotal().compareTo(o1.getValorTotal()))
				.collect(Collectors.toList())
			.subList(0, 3);
	}
	
	private List<ProdutoDTO> toProductsFromBuyings(List<Buying> compras) {
		return compras.stream().map(this::toProductFromBuying).collect(Collectors.toList());
	}
	
	private ProdutoDTO toProductFromBuying(Buying buying) {
		Optional<Product> optional = this.productService.getProdutoById(buying.getCodigo());
		
		return toProductFromBuyingAndOptional(buying, optional);
	}
	
	private ProdutoDTO toProductFromBuyingAndOptional(Buying c, Optional<Product> optional) {
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

	private Product convertBuyingIntoProduct(Buying buying) {
		Product product = this.productService.getProdutoById(buying.getCodigo()).orElseThrow();
		product.setQuantidadeComprada(buying.getQuantidade());
		return product;
	}
}
