package com.projeto.spring.lojavirtual.controller;
import java.net.URI;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.spring.lojavirtual.modelo.entidade.Itens;
import com.projeto.spring.lojavirtual.modelo.entidade.Pedido;
import com.projeto.spring.lojavirtual.modelo.entidade.dto.ItensAlterarQuantidadeDTO;
import com.projeto.spring.lojavirtual.modelo.entidade.dto.ItensDTO;
import com.projeto.spring.lojavirtual.modelo.entidade.dto.ItensInserirDTO;
import com.projeto.spring.lojavirtual.service.ItensService;
import com.projeto.spring.lojavirtual.service.PedidoService;

@RestController
public class ItensController {

	@Autowired
	private ItensService itensService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/{idPedido}/itens")
	public ResponseEntity<Itens> inserir(@Valid @RequestBody ItensInserirDTO itensInserirDTO,@PathVariable Long idPedido) {
		Itens itens = paraInserirDto(itensInserirDTO);
		itens = itensService.inserir(itens,idPedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(itens.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/{idPedido}/itens/{idItem}")
	public ResponseEntity<Void> deletar(@PathVariable Long idPedido,
			@PathVariable Long idItem) {
		Itens itens = itensService.consultarPorId(idPedido, idItem);
		itensService.deletar(itens);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{idPedido}/itens/{idItem}/alterar")
	public ResponseEntity<Itens> alterarQuantidade(
			@Valid @RequestBody ItensAlterarQuantidadeDTO alterarQuantidadeDTO,
			@PathVariable Long idPedido,
			@PathVariable Long idItem) {
		Pedido pedido = itensService.consultarPorId(idPedido, idItem).getPedido();
		Itens itens = paraAtualizarDto(alterarQuantidadeDTO);
		itens.setPedido(pedido);
		pedido.getItens().add(itens);
		itensService.atualizar(idItem, itens, idPedido);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/{idPedido}/pedidos/{idItens}/itens")
	public ResponseEntity<ItensDTO> consultarPorId(@PathVariable Long idPedido,
			@PathVariable Long idItens) {
		Itens itens = itensService.consultarPorId(idPedido, idItens);
		itens.setPedido(pedidoService.buscarPorId(idPedido));
		ItensDTO itensDto = paraVisualizacaoDto(itens);
		return ResponseEntity.ok().body(itensDto);
	}
	
	public Itens paraInserirDto(ItensInserirDTO inserirDTO) {
		return modelMapper.map(inserirDTO,Itens.class);
	}
	
	public Itens paraAtualizarDto(ItensAlterarQuantidadeDTO alterarQuantidadeDTO) {
		return modelMapper.map(alterarQuantidadeDTO,Itens.class);
	}
	
	public ItensDTO paraVisualizacaoDto(Itens itens) {
		return modelMapper.map(itens,ItensDTO.class);
	}

}
