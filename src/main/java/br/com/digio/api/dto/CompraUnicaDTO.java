package br.com.digio.api.dto;

import java.math.BigDecimal;
import java.util.Optional;

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
public class CompraUnicaDTO {

	@Getter
	@Setter
	private String nome;
	
	@Getter
	@Setter
	private String cpf;
	
	@Setter
	private BigDecimal valorTotal;
	
	@Getter
	@Setter
	private Integer codigo;
	
	@Getter
	@Setter
    private Long quantidade;
	
	@Getter
	@Setter
	private String tipoVinho;
	
	@Getter
	@Setter
    private BigDecimal precoUnitario;
	
	@Getter
	@Setter
    private String safra;
	
	@Getter
	@Setter
	private Integer anoCompra;
	
	public BigDecimal getValorTotal() {
		return this.precoUnitario.multiply(BigDecimal.valueOf(Optional.ofNullable(this.quantidade).orElse(0L)));
	}
}
