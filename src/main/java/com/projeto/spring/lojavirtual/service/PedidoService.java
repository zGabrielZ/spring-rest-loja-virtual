package com.projeto.spring.lojavirtual.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.spring.lojavirtual.modelo.entidade.Pedido;
import com.projeto.spring.lojavirtual.repositorio.PedidoRepositorio;
import com.projeto.spring.lojavirtual.service.exceptions.EntidadeNaoEncontrado;

@Service
public class PedidoService {	

	@Autowired
	private PedidoRepositorio pedidoRepositorio;
	
	public List<Pedido> listagem(){
		return pedidoRepositorio.findAll();
	}
	
	public Pedido buscarPorId(Long id) {
		Optional<Pedido> pedido = pedidoRepositorio.findById(id);
		if(!pedido.isPresent()) {
			throw new EntidadeNaoEncontrado("Pedido não encontrado");
		}
		
		return pedido.get();
	}
}
