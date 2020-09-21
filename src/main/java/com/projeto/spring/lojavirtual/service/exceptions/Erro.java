package com.projeto.spring.lojavirtual.service.exceptions;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Erro {

	private Integer status;
	private ZonedDateTime data;
	private String titulo;
	private List<Campo> campos = new ArrayList<Erro.Campo>();
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Campo{
		private String nome;
		private String mensagem;	
	}
}
