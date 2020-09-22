package com.projeto.spring.lojavirtual.modelo.entidade.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItensDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;	
	private Integer quantidade;
	private ProdutoDTO produto;
	
	public Double getSubTotal() {
		return quantidade * produto.getPreco();
	}
}
