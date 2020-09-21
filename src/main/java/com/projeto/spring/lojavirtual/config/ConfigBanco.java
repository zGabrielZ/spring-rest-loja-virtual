package com.projeto.spring.lojavirtual.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.projeto.spring.lojavirtual.modelo.entidade.Usuario;
import com.projeto.spring.lojavirtual.repositorio.UsuarioRepositorio;

@Configuration
public class ConfigBanco implements CommandLineRunner{

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Override
	public void run(String... args) throws Exception {
		Usuario usuario = new Usuario(null,"José Pereira","jose@gmail.com","123");
		Usuario usuario2 = new Usuario(null,"Maria Joaquina","maria@gmail.com","123");
		Usuario usuario3 = new Usuario(null,"Marcos Fernando","marcos@gmail.com","123");
		
		usuarioRepositorio.saveAll(Arrays.asList(usuario,usuario2,usuario3));
	}

}
