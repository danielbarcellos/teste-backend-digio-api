package br.com.digio.api.service.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

	private String nome;
	private String cpf;
	private List<Buying> compras;
}
