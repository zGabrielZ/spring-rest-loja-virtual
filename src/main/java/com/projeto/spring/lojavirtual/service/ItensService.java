package com.projeto.spring.lojavirtual.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.spring.lojavirtual.modelo.entidade.Itens;
import com.projeto.spring.lojavirtual.modelo.entidade.Pedido;
import com.projeto.spring.lojavirtual.modelo.entidade.Produto;
import com.projeto.spring.lojavirtual.repositorio.ItensRepositorio;
import com.projeto.spring.lojavirtual.service.exceptions.EntidadeNaoEncontrado;
import com.projeto.spring.lojavirtual.service.exceptions.RegraDeNegocio;

@Service
public class ItensService {	

	@Autowired
	private ItensRepositorio itemsRepositorio;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	public Itens consultarPorId(Long idPedido,Long idItens) {
		Pedido pedido = pedidoService.buscarPorId(idPedido);
		Optional<Itens> itens = itemsRepositorio.verificarPedidoItens(pedido.getId(), idItens);
		if(!itens.isPresent()) {
			throw new EntidadeNaoEncontrado("Itens não encontrado");
		}
		
		return itens.get();
	}
	
	@Transactional
	public Itens inserir(Itens itens,Long idPedido) {
		Pedido pedido = pedidoService.buscarPorId(idPedido);
		
		itens.setPedido(pedido);
		pedido.getItens().add(itens);
		
		Produto produto = produtoService.buscarPorId(itens.getProduto().getId());
		itens.setProduto(produto);
		produto.getItens().add(itens);
		
		validarEstoque(itens);
		
		return itemsRepositorio.save(itens);
	}
	
	public void validarEstoque(Itens itens) {
		Integer estoqueAtual = itens.getProduto().getEstoque() - itens.getQuantidade();
		itens.getProduto().setEstoque(estoqueAtual);
		
		if(estoqueAtual<0) {
			throw new RegraDeNegocio("Não podemos inserir, pois o estoque já chegou a zero");
		}
	}
	
	@Transactional
	public void deletar(Itens itens) {
		
		Produto produto = produtoService.buscarPorId(itens.getProduto().getId());
		itens.setProduto(produto);
		produto.getItens().add(itens);
		
		Integer estoque = itens.getProduto().getEstoque();
		Integer estoqueAtual = estoque += itens.getQuantidade();
		itens.getProduto().setEstoque(estoqueAtual);
		
		itemsRepositorio.deleteById(itens.getId());
	}
	
	@Transactional
	public Itens atualizar(Long idItens,Itens itens,Long idPedido) {
		try {
			Itens entidade = itemsRepositorio.getOne(idItens);	
			updateData(entidade,itens);
			
			Produto produto = produtoService.buscarPorId(entidade.getProduto().getId());
			itens.setProduto(produto);
			produto.getItens().add(itens);
			
			validarEstoque(itens);
			
			return itemsRepositorio.save(entidade);
		} catch (EntityNotFoundException e) {
			throw new EntidadeNaoEncontrado("Itém não encontrado");
		}
	}

	private void updateData(Itens entidade, Itens itens) {
		entidade.setQuantidade(itens.getQuantidade());
	}
	

}
