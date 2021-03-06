package com.projeto.spring.lojavirtual.service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.spring.lojavirtual.modelo.entidade.Itens;
import com.projeto.spring.lojavirtual.modelo.entidade.Pedido;
import com.projeto.spring.lojavirtual.modelo.entidade.Produto;
import com.projeto.spring.lojavirtual.modelo.entidade.Usuario;
import com.projeto.spring.lojavirtual.modelo.entidade.enums.PedidoStatus;
import com.projeto.spring.lojavirtual.repositorio.ItensRepositorio;
import com.projeto.spring.lojavirtual.repositorio.PedidoRepositorio;
import com.projeto.spring.lojavirtual.service.exceptions.EntidadeNaoEncontrado;
import com.projeto.spring.lojavirtual.service.exceptions.RegraDeNegocio;

@Service
public class PedidoService {	

	@Autowired
	private PedidoRepositorio pedidoRepositorio;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItensRepositorio itensRepositorio;
	
	@Transactional
	public Pedido inserir(Pedido pedido) {

		validarNumeroDoPedido(pedido.getNumeroDoPedido());

		pedido.setDataDoPedido(new Date());
		pedido.setPedidoStatus(PedidoStatus.ABERTA);
		
		Usuario usuario = usuarioService.buscarPorId(pedido.getUsuario().getId());
		
		pedido.setUsuario(usuario);
		usuario.getPedidos().add(pedido);
		
		pedido = pedidoRepositorio.save(pedido);
		for(Itens itens : pedido.getItens()) {
			Produto produto = produtoService.buscarPorId(itens.getProduto().getId());
			itens.setProduto(produto);
			itens.setPreco(produto.getPreco());
			itens.setPedido(pedido);
			
			Integer estoqueAtual = itens.getProduto().getEstoque() - itens.getQuantidade();
			itens.getProduto().setEstoque(estoqueAtual);
			
			if(estoqueAtual<0) {
				throw new RegraDeNegocio("Não podemos inserir, pois o estoque já chegou a zero");
			}
			
		}
		
		itensRepositorio.saveAll(pedido.getItens());
		return pedido;
		
	}
	
	public void alterarDadosDoPedido(Long id) {
		Optional<Pedido> pedido = pedidoRepositorio.findById(id);
		if(!pedido.isPresent()) {
			throw new EntidadeNaoEncontrado("Pedido não encontrado");
		}
		
		pedido.get().finalizar();
		
		pedidoRepositorio.save(pedido.get());
	}
	
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
	
	@Transactional
	public Pedido cancelar(Pedido pedido) {
		
		buscarPorId(pedido.getId());
		
		pedido.cancelar();
		
		pedido = pedidoRepositorio.save(pedido);
		
		for(Itens itens : pedido.getItens()) {
			
			Produto produto = produtoService.buscarPorId(itens.getProduto().getId());
			itens.setProduto(produto);
			itens.setPreco(produto.getPreco());
			itens.setPedido(pedido);
			
			Integer estoque = itens.getProduto().getEstoque(); 
			Integer estoqueAtual = estoque += itens.getQuantidade();
			itens.getProduto().setEstoque(estoqueAtual);
			
		}
		
		itensRepositorio.saveAll(pedido.getItens());
		return pedido;
		
	}
	
	public void validarNumeroDoPedido(Long numeroDoPedido) {
		boolean numeroDoPedidoPedido = pedidoRepositorio.existsByNumeroDoPedido(numeroDoPedido);
		if(numeroDoPedidoPedido) {
			throw new RegraDeNegocio("Já existe este número do pedido cadastrado, por favor tente novamente");
		}
	}
}
