package com.projeto.spring.lojavirtual.controller;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.spring.lojavirtual.modelo.entidade.Produto;
import com.projeto.spring.lojavirtual.modelo.entidade.dto.ProdutoDTO;
import com.projeto.spring.lojavirtual.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {	

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id){
		Produto produto = produtoService.buscarPorId(id);
		return ResponseEntity.ok().body(paraVisualizacaoDto(produto));
	}
	
	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> listagem(){
		List<Produto> produtos = produtoService.listagem();
		return ResponseEntity.ok().body(paraListaDto(produtos));
	}
	
	public ProdutoDTO paraVisualizacaoDto(Produto produto) {
		return modelMapper.map(produto,ProdutoDTO.class);
	}
	
	private List<ProdutoDTO> paraListaDto(List<Produto> produtos) {
		return produtos.stream()
				.map(produto -> paraVisualizacaoDto(produto))
				.collect(Collectors.toList());
	}
}
