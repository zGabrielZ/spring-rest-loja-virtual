package com.projeto.spring.lojavirtual.modelo.entidade.dto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItensInserirDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Min(value = 1,message = "Mínimo é de 1 quantidade")
	@NotNull(message = "Quantidade não pode ser nulo")
	private Integer quantidade;
	
	@Valid
	@NotNull(message = "Produto não pode ser nulo")
	private ProdutoNovoId produto;
}
