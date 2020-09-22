package com.projeto.spring.lojavirtual.service;
import java.util.Date;
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
	
	public List<Pedido> listagem(Long idUsuario) {
		return pedidoRepositorio.getPedidos(idUsuario);
	}
	
	public List<Pedido> listagemPedidosCancelados(Long idUsuario) {
		return pedidoRepositorio.getPedidosCancelados(idUsuario);
	}
	
	public Pedido buscarPorId(Long id) {
		Optional<Pedido> pedido = pedidoRepositorio.findById(id);
		if(!pedido.isPresent()) {
			throw new EntidadeNaoEncontrado("Pedido não encontrado");
		}
		
		return pedido.get();
	}
	
	public List<Pedido> filtrarPeriodo(Date minDate, Date maxDate,Long idUsuario) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		return pedidoRepositorio.filtrarPeriodo(minDate, maxDate,idUsuario);
	}
}
