package com.projeto.spring.lojavirtual.service.exceptions;

public class EntidadeNaoEncontrado extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontrado(String msg) {
		super(msg);
	}
}
