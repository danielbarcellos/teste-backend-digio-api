package br.com.digio.api.model;

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
@EqualsAndHashCode(of = "codigo")
public class Produto {

	private Integer codigo;
    private String tipo_vinho;
    private BigDecimal preco;
    private String safra;
    private Integer ano_compra;
}
