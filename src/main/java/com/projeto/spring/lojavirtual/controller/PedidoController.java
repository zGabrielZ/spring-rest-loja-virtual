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

import com.projeto.spring.lojavirtual.modelo.entidade.Pedido;
import com.projeto.spring.lojavirtual.modelo.entidade.Usuario;
import com.projeto.spring.lojavirtual.modelo.entidade.dto.PedidoDTO;
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
	
	
	@GetMapping("/{id}")
	public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id){
		Pedido pedido = pedidoService.buscarPorId(id);
		return ResponseEntity.ok().body(paraVisualizacaoDto(pedido));
	}
	
	public PedidoDTO paraVisualizacaoDto(Pedido pedido) {
		return modelMapper.map(pedido,PedidoDTO.class);
	}
	
	private List<PedidoDTO> paraListaDto(List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> paraVisualizacaoDto(pedido))
				.collect(Collectors.toList());
	}
}
