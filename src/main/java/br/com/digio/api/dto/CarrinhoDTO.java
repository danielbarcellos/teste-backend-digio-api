
package br.com.digio.api.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.digio.api.model.Produto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class CarrinhoDTO {

	@Getter
	@Setter
    private ClienteDTO cliente;
	
	@Getter
	@Setter
    private List<Produto> produtos;
	
	public BigDecimal getValorTotal() {
		return this.produtos.stream().map(Produto::getPreco).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public Integer getQuantidade() {
		return this.produtos.size();
	}
}
