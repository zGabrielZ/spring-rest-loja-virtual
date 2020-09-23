package com.projeto.spring.lojavirtual.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.spring.lojavirtual.modelo.entidade.Usuario;
import com.projeto.spring.lojavirtual.repositorio.UsuarioRepositorio;
import com.projeto.spring.lojavirtual.service.exceptions.EntidadeNaoEncontrado;
import com.projeto.spring.lojavirtual.service.exceptions.ErroAutenticacao;
import com.projeto.spring.lojavirtual.service.exceptions.RegraDeNegocio;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	public void validarEmail(String email) {
		boolean usuarioEmail = usuarioRepositorio.existsByEmail(email);
		if(usuarioEmail) {
			throw new RegraDeNegocio("Já existe este usuário cadastrado com este email, por favor tente novamente");
		}
	}
	
	public Usuario inserir(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return usuarioRepositorio.save(usuario);
	} 
	
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = usuarioRepositorio.findByEmail(email);
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado para o email informado");
		}
		
		if(!usuario.get().getSenha().equals(senha)){
			throw new ErroAutenticacao("Senha inválida");
		}
		
		return usuario.get();
	}
	
	public Usuario buscarPorId(Long id) {
		Optional<Usuario> usuario = usuarioRepositorio.findById(id);
		if(!usuario.isPresent()) {
			throw new EntidadeNaoEncontrado("Usuário não encontrado");
		}
		
		return usuario.get();
	}
	
	public List<Usuario> listagem(){
		return usuarioRepositorio.findAll();
	}
}
