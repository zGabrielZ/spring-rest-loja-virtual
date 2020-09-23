package com.projeto.spring.lojavirtual.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.spring.lojavirtual.modelo.entidade.Itens;
import com.projeto.spring.lojavirtual.modelo.entidade.Pedido;
import com.projeto.spring.lojavirtual.repositorio.ItensRepositorio;
import com.projeto.spring.lojavirtual.service.exceptions.EntidadeNaoEncontrado;

@Service
public class ItensService {	

	@Autowired
	private ItensRepositorio itemsRepositorio;
	
	@Autowired
	private PedidoService pedidoService;
	
	public Itens consultarPorId(Long idPedido,Long idItens) {
		Pedido pedido = pedidoService.buscarPorId(idPedido);
		Optional<Itens> itens = itemsRepositorio.verificarPedidoItens(pedido.getId(), idItens);
		if(!itens.isPresent()) {
			throw new EntidadeNaoEncontrado("Itens não encontrado");
		}
		
		return itens.get();
	}
	
	public Itens inserir(Itens itens,Long idPedido) {
		Pedido pedido = pedidoService.buscarPorId(idPedido);
		itens.setPedido(pedido);
		return itemsRepositorio.save(itens);
	}
	

}
