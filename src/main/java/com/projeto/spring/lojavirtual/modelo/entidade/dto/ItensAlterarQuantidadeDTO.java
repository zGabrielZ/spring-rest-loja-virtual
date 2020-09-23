package com.projeto.spring.lojavirtual.modelo.entidade.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItensAlterarQuantidadeDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Quantidade não pode ser nulo")
	private Integer quantidade;
}
