package br.com.digio.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cpf")
public class CompraDTO {

	@Getter
	@Setter
	private String nome;
	
	@Getter
	@Setter
	private String cpf;
	
	@Setter
	private BigDecimal valorTotal;
	
	@Setter
	private List<ProdutoDTO> produtos = new ArrayList<>();

	public BigDecimal getValorTotal() {
		if(this.produtos == null ) {
			return BigDecimal.ZERO;
		}
		
		return produtos.stream().map(p -> p.getPrecoTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public List<ProdutoDTO> getProdutos() {
		if(this.produtos == null) {
			return new ArrayList<>();
		}
		return produtos;
	}
}
