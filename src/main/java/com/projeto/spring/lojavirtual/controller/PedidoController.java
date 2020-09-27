package com.projeto.spring.lojavirtual.controller;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.spring.lojavirtual.controller.utils.URL;
import com.projeto.spring.lojavirtual.modelo.entidade.Itens;
import com.projeto.spring.lojavirtual.modelo.entidade.Pedido;
import com.projeto.spring.lojavirtual.modelo.entidade.Usuario;
import com.projeto.spring.lojavirtual.modelo.entidade.dto.ItensDTO;
import com.projeto.spring.lojavirtual.modelo.entidade.dto.PedidoDTO;
import com.projeto.spring.lojavirtual.modelo.entidade.dto.PedidoInserirDTO;
import com.projeto.spring.lojavirtual.service.PedidoService;
import com.projeto.spring.lojavirtual.service.UsuarioService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {	

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<Pedido> cadastrar(@Valid @RequestBody PedidoInserirDTO pedidoInserirDTO){
		Pedido pedido = paraInserirDto(pedidoInserirDTO);
		pedido = pedidoService.inserir(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
		
	@GetMapping("/{idUsuario}/lista")
	public ResponseEntity<List<PedidoDTO>> listagem(@PathVariable Long idUsuario) {
		Usuario usuario = usuarioService.buscarPorId(idUsuario);
		return ResponseEntity.ok().body(paraListaDto(pedidoService.listagem(usuario.getId())));
	}
	
	@GetMapping("/{idUsuario}/lista/cancelados")
	public ResponseEntity<List<PedidoDTO>> listagemPedidosCancelados(@PathVariable Long idUsuario) {
		Usuario usuario = usuarioService.buscarPorId(idUsuario);
		return ResponseEntity.ok().body(paraListaDto(pedidoService.listagemPedidosCancelados(usuario.getId())));
	}
	
	@GetMapping("/{idUsuario}/buscar")
	public ResponseEntity<List<PedidoDTO>> filtrarPeriodo(
			@PathVariable Long idUsuario,
			@RequestParam(value = "min",defaultValue = "") String minDate,
			@RequestParam(value = "max",defaultValue = "") String maxDate){
		Date min = URL.convertDate(minDate,new Date(0L));
		Date max = URL.convertDate(maxDate,new Date());
		Usuario usuario = usuarioService.buscarPorId(idUsuario);
		List<Pedido> pedidos = pedidoService.filtrarPeriodo(min, max, usuario.getId());
		return ResponseEntity.ok().body(paraListaDto(pedidos));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id){
		Pedido pedido = pedidoService.buscarPorId(id);
		return ResponseEntity.ok().body(paraVisualizacaoDto(pedido));
	}
		
	@PutMapping("/{pedidoId}/cancelar")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void paralisar(@PathVariable Long pedidoId) {
		Pedido pedido = pedidoService.buscarPorId(pedidoId);
		pedidoService.cancelar(pedido);
	}
	
	
	@PutMapping("/{pedidoId}/alterar")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void alterar(@PathVariable Long pedidoId) {
		pedidoService.alterarDadosDoPedido(pedidoId);
	}
	
	@GetMapping("/{idPedido}/itens")
	public ResponseEntity<List<ItensDTO>> listagemDeItens(@PathVariable Long idPedido) {
		Pedido pedido = pedidoService.buscarPorId(idPedido);
		return ResponseEntity.ok().body(paraListaItensDto(pedido.getItens()));
	}
	
	public Pedido paraInserirDto(PedidoInserirDTO inserirDTO) {
		return modelMapper.map(inserirDTO,Pedido.class);
	}
	
	public PedidoDTO paraVisualizacaoDto(Pedido pedido) {
		return modelMapper.map(pedido,PedidoDTO.class);
	}
	
	private List<PedidoDTO> paraListaDto(List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> paraVisualizacaoDto(pedido))
				.collect(Collectors.toList());
	}
	
	public ItensDTO paraVisualizacaoItensDto(Itens itens) {
		return modelMapper.map(itens,ItensDTO.class);
	}
	
	private List<ItensDTO> paraListaItensDto(List<Itens> itens) {
		return itens.stream()
				.map(item -> paraVisualizacaoItensDto(item))
				.collect(Collectors.toList());
	}
}
