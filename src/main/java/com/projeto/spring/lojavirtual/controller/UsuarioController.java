package com.projeto.spring.lojavirtual.controller;

import java.net.URI;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.spring.lojavirtual.modelo.entidade.Usuario;
import com.projeto.spring.lojavirtual.modelo.entidade.dto.UsuarioDTO;
import com.projeto.spring.lojavirtual.modelo.entidade.dto.UsuarioInserirDTO;
import com.projeto.spring.lojavirtual.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<Usuario> cadastrar(@Valid @RequestBody UsuarioInserirDTO usuarioInserirDTO){
		Usuario usuario = paraInserirDto(usuarioInserirDTO);
		usuario = usuarioService.inserir(usuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id){
		Usuario usuario = usuarioService.buscarPorId(id);
		return ResponseEntity.ok().body(paraVisualizacaoDto(usuario));
	}
	
	public Usuario paraInserirDto(UsuarioInserirDTO inserirDTO) {
		return modelMapper.map(inserirDTO,Usuario.class);
	}
	
	public UsuarioDTO paraVisualizacaoDto(Usuario usuario) {
		return modelMapper.map(usuario,UsuarioDTO.class);
	}
}
