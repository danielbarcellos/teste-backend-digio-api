package br.com.digio.api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "produtoId")
public class ClienteDTO {

	private String nome;
	
	private String cpf;
	
	private ProdutoDTO produto;
	
	private Long quantidade;
	
	private BigDecimal valorTotal;
}
