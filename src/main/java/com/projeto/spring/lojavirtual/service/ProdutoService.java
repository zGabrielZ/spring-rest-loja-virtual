package com.projeto.spring.lojavirtual.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.spring.lojavirtual.modelo.entidade.Produto;
import com.projeto.spring.lojavirtual.repositorio.ProdutoRepositorio;
import com.projeto.spring.lojavirtual.service.exceptions.EntidadeNaoEncontrado;

@Service
public class ProdutoService {	

	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	public List<Produto> listagem(){
		return produtoRepositorio.findAll();
	}
	
	public Produto buscarPorId(Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		if(!produto.isPresent()) {
			throw new EntidadeNaoEncontrado("Produto não encontrado");
		}
		
		return produto.get();
	}
	

}
