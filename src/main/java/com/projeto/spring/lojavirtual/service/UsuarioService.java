package com.projeto.spring.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.spring.lojavirtual.repositorio.UsuarioRepositorio;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
}
