package com.projeto.spring.lojavirtual.controller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.spring.lojavirtual.modelo.entidade.Itens;
import com.projeto.spring.lojavirtual.modelo.entidade.dto.ItensDTO;
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
	
	@GetMapping("/{idPedido}/pedidos/{idItens}/itens")
	public ResponseEntity<ItensDTO> consultarPorId(@PathVariable Long idPedido,
			@PathVariable Long idItens) {
		Itens itens = itensService.consultarPorId(idPedido, idItens);
		itens.setPedido(pedidoService.buscarPorId(idPedido));
		ItensDTO itensDto = paraVisualizacaoDto(itens);
		return ResponseEntity.ok().body(itensDto);
	}
	
	
	public ItensDTO paraVisualizacaoDto(Itens itens) {
		return modelMapper.map(itens,ItensDTO.class);
	}

}
