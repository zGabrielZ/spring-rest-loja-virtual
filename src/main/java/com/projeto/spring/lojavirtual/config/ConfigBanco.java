package com.projeto.spring.lojavirtual.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.projeto.spring.lojavirtual.modelo.entidade.Produto;
import com.projeto.spring.lojavirtual.modelo.entidade.Usuario;
import com.projeto.spring.lojavirtual.repositorio.ProdutoRepositorio;
import com.projeto.spring.lojavirtual.repositorio.UsuarioRepositorio;

@Configuration
public class ConfigBanco implements CommandLineRunner{
	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	

	@Override
	public void run(String... args) throws Exception {
		Usuario usuario = new Usuario(null,"José Pereira","764.791.460-00","jose@gmail.com","123");

		Produto produto1 = new Produto(null,"Camiseta",30.00,100);
		Produto produto2 = new Produto(null,"Camiseta Regata",30.00,80);
		Produto produto3 = new Produto(null,"Camiseta Polo",45.00,100);
		Produto produto4 = new Produto(null,"Camiseta Manga Longa",35.00,50);
		Produto produto5 = new Produto(null,"Camisa",180.00,120);
		Produto produto6 = new Produto(null,"Suéter",160.00,25);
		Produto produto7 = new Produto(null,"Calça",120.00,80);
		Produto produto8 = new Produto(null,"Bermuda",80.00,60);
		Produto produto9 = new Produto(null,"Shorts",70.00,60);
		Produto produto10 = new Produto(null,"Meia",30.00,100);
		Produto produto11 = new Produto(null,"Gravata",150.00,30);
		Produto produto12 = new Produto(null,"Cinto",90.00,60);
		Produto produto13 = new Produto(null,"Carteira",110.00,20);

		produtoRepositorio.saveAll(Arrays.asList(produto1,produto2,produto3,produto4,produto5,produto6,
				produto7,produto8,produto9,produto10,produto11,produto12,produto13));
		usuarioRepositorio.saveAll(Arrays.asList(usuario));
	}
	
	
}
