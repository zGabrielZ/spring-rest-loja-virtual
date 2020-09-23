package com.projeto.spring.lojavirtual.modelo.entidade.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioAutenticarDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Email(message = "Email inválido")
	@NotBlank(message = "Campo do email não pode ser vazio")
	@Size(max = 120,message = "Não pode passa de 150 caracteres")
	private String email;
	
	@NotBlank(message = "Campo da senha não pode ser vazio")
	@Size(max = 120,message = "Não pode passa de 120 caracteres")
	private String senha;
	
}
