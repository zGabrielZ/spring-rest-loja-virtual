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
public class ProdutoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private Double preco;
	private Integer estoque;
	
}
