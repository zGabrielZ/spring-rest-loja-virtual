package com.projeto.spring.lojavirtual.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.spring.lojavirtual.modelo.entidade.Itens;
import com.projeto.spring.lojavirtual.modelo.entidade.Pedido;
import com.projeto.spring.lojavirtual.modelo.entidade.Produto;
import com.projeto.spring.lojavirtual.modelo.entidade.enums.PedidoStatus;
import com.projeto.spring.lojavirtual.repositorio.ItensRepositorio;
import com.projeto.spring.lojavirtual.repositorio.ProdutoRepositorio;
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
	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
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
		itens.setPreco(produto.getPreco());
		produto.getItens().add(itens);
		
		validarEstoque(itens);
		
		validarPedido(itens);
		
		return itemsRepositorio.save(itens);
	}
			
	@Transactional
	public void deletar(Itens itens) {
		
		Produto produto = produtoService.buscarPorId(itens.getProduto().getId());
		itens.setProduto(produto);
		produto.getItens().add(itens);
		
		Integer estoque = itens.getProduto().getEstoque(); 
		Integer estoqueAtual = estoque += itens.getQuantidade();
		itens.getProduto().setEstoque(estoqueAtual);
		
		validarPedido(itens);
		
		itemsRepositorio.deleteById(itens.getId());
	}
	
	public Itens atualizar(Long idItens,Itens novoItens) {
		Optional<Itens> antigoItens = itemsRepositorio.findById(idItens);
		if(antigoItens.isPresent()) {
			
			Itens itens = antigoItens.get();
			
			
			Produto produto = produtoService.buscarPorId(itens.getProduto().getId());
			itens.setProduto(produto);
			
			validarEstoqueAtualizar(itens,novoItens.getQuantidade());
			itens.setQuantidade(novoItens.getQuantidade());
			
			produtoRepositorio.save(produto);
			return itemsRepositorio.save(itens);
			
		} else {
			throw new EntidadeNaoEncontrado("Item não encontrado");
		}
	}
	
	
	public void validarEstoqueAtualizar(Itens itens,Integer quantidade) {
		
		Integer estoqueAtual = 0;
		
		if(itens.getQuantidade() > quantidade) {
			Integer quantidadeAtual = itens.getQuantidade() - quantidade; 
			estoqueAtual = itens.getProduto().getEstoque() + quantidadeAtual;
			itens.getProduto().setEstoque(estoqueAtual);
		}
		
		if(itens.getQuantidade() < quantidade) {
			Integer quantidadeAtual = quantidade - itens.getQuantidade();
			estoqueAtual = itens.getProduto().getEstoque() - quantidadeAtual;
			itens.getProduto().setEstoque(estoqueAtual);
		}
		
		validarPedido(itens);
		
		if(estoqueAtual<0) {
			throw new RegraDeNegocio("Não podemos inserir, pois o estoque já chegou a zero");
		}
	}
	
	public void validarPedido(Itens itens) {
		if(itens.getPedido().getPedidoStatus().equals(PedidoStatus.FINALIZADA)) {
			throw new RegraDeNegocio("Não podemos inserir, pois já está finalizada ");
		}
		
		if(itens.getPedido().getPedidoStatus().equals(PedidoStatus.CANCELADA)) {
			throw new RegraDeNegocio("Não podemos inserir, pois já está cancelada ");
		}
		
	}
	
	public void validarEstoque(Itens itens) {
		Integer estoqueAtual = itens.getProduto().getEstoque() - itens.getQuantidade();
		itens.getProduto().setEstoque(estoqueAtual);
		
		if(estoqueAtual<0) {
			throw new RegraDeNegocio("Não podemos inserir, pois o estoque já chegou a zero");
		}
	}
	
	

}
