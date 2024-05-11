package br.com.digio.api.service.model;

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
public class Product {

	private Integer codigo;
    private String tipo_vinho;
    private BigDecimal preco;
    private String safra;
    private Integer ano_compra;
    private Long quantidadeComprada;
}
