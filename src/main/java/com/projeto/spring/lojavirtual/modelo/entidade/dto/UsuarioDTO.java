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
public class UsuarioDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String cpf;
	private String email;
	
}
