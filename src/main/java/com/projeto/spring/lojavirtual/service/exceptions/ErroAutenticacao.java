package com.projeto.spring.lojavirtual.service.exceptions;

public class ErroAutenticacao extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ErroAutenticacao(String msg) {
		super(msg);
	}
}
