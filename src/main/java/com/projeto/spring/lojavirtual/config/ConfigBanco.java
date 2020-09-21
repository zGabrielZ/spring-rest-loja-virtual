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
		Usuario usuario = new Usuario(null,"José Pereira","764.791.460-00","jose@gmail.com","123");
		Usuario usuario2 = new Usuario(null,"Maria Joaquina","490.923.940-50","maria@gmail.com","123");
		Usuario usuario3 = new Usuario(null,"Marcos Fernando","842.649.800-03","marcos@gmail.com","123");
		
		usuarioRepositorio.saveAll(Arrays.asList(usuario,usuario2,usuario3));
	}

}
