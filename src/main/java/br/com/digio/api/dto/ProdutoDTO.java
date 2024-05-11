package br.com.digio.api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

	private Integer codigo;
    private Long quantidade;
	private String tipoVinho;
    private BigDecimal precoTotal;
    private String safra;
}
