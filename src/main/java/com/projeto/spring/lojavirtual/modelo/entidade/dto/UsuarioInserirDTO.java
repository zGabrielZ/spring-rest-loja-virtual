package com.projeto.spring.lojavirtual.modelo.entidade.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioInserirDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Campo do nome não pode ser vazio")
	@Size(max = 120,message = "Não pode passa de 120 caracteres")
	private String nome;
	
	@CPF(message = "Cpf inválido")
	@NotBlank(message = "Campo do cpf não pode ser vazio")
	private String cpf;
	
	@Email(message = "Email inválido")
	@NotBlank(message = "Campo do email não pode ser vazio")
	@Size(max = 120,message = "Não pode passa de 150 caracteres")
	private String email;
	
	@NotBlank(message = "Campo da senha não pode ser vazio")
	@Size(max = 120,message = "Não pode passa de 120 caracteres")
	private String senha;
	
}
