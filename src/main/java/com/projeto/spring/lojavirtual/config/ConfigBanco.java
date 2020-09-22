package com.projeto.spring.lojavirtual.config;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.projeto.spring.lojavirtual.modelo.entidade.Itens;
import com.projeto.spring.lojavirtual.modelo.entidade.Pedido;
import com.projeto.spring.lojavirtual.modelo.entidade.Produto;
import com.projeto.spring.lojavirtual.modelo.entidade.Usuario;
import com.projeto.spring.lojavirtual.modelo.entidade.enums.PedidoStatus;
import com.projeto.spring.lojavirtual.repositorio.ItensRepositorio;
import com.projeto.spring.lojavirtual.repositorio.PedidoRepositorio;
import com.projeto.spring.lojavirtual.repositorio.ProdutoRepositorio;
import com.projeto.spring.lojavirtual.repositorio.UsuarioRepositorio;

@Configuration
public class ConfigBanco implements CommandLineRunner{

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@Autowired
	private PedidoRepositorio pedidoRepositorio;

	@Autowired
	private ItensRepositorio itensRepositorio;
	
	@Override
	public void run(String... args) throws Exception {
		Usuario usuario = new Usuario(null,"José Pereira","764.791.460-00","jose@gmail.com","123");
		Usuario usuario2 = new Usuario(null,"Maria Joaquina","490.923.940-50","maria@gmail.com","123");
		Usuario usuario3 = new Usuario(null,"Marcos Fernando","842.649.800-03","marcos@gmail.com","123");
		
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedido = new Pedido(null,sdf.parse("19/09/2020 10:00"),null,PedidoStatus.ABERTA, usuario);
		Pedido pedido2 = new Pedido(null,sdf.parse("20/09/2020 12:00"),null,PedidoStatus.CANCELADA, usuario);
		Pedido pedido3 = new Pedido(null,sdf.parse("21/09/2020 14:00"),null,PedidoStatus.CANCELADA, usuario);
		
		Pedido pedido4 = new Pedido(null,sdf.parse("17/09/2020 10:00"),null,PedidoStatus.ABERTA, usuario2);
		Pedido pedido5 = new Pedido(null,sdf.parse("18/09/2020 10:00"),null,PedidoStatus.CANCELADA, usuario2);
		
		usuario.getPedidos().addAll(Arrays.asList(pedido,pedido2,pedido3));
		usuario2.getPedidos().addAll(Arrays.asList(pedido4,pedido5));
		
		Itens itens = new Itens(null,2,produto1.getPreco(), produto1, pedido);
		Itens itens2 = new Itens(null,4,produto4.getPreco(), produto4, pedido);
		
		Itens itens3 = new Itens(null,2,produto13.getPreco(), produto13, pedido2);
		Itens itens4 = new Itens(null,6,produto6.getPreco(), produto6, pedido3);
		
		Itens itens5 = new Itens(null,2,produto8.getPreco(), produto8, pedido4);
		Itens itens6 = new Itens(null,2,produto3.getPreco(), produto3, pedido4);
		Itens itens7 = new Itens(null,4,produto13.getPreco(), produto13, pedido5);
		
		pedido.getItens().addAll(Arrays.asList(itens,itens2));
		pedido2.getItens().addAll(Arrays.asList(itens3));
		pedido3.getItens().addAll(Arrays.asList(itens4));
		pedido4.getItens().addAll(Arrays.asList(itens5,itens6));
		pedido5.getItens().addAll(Arrays.asList(itens7));
		
		produto1.getItens().add(itens);
		produto4.getItens().add(itens2);
		produto13.getItens().addAll(Arrays.asList(itens3,itens7));
		produto6.getItens().add(itens4);
		produto8.getItens().add(itens5);
		produto3.getItens().add(itens6);
		
		produtoRepositorio.saveAll(Arrays.asList(produto1,produto2,produto3,produto4,produto5,produto6,
				produto7,produto8,produto9,produto10,produto11,produto12,produto13));
		usuarioRepositorio.saveAll(Arrays.asList(usuario,usuario2,usuario3));
		pedidoRepositorio.saveAll(Arrays.asList(pedido,pedido2,pedido3,pedido4,pedido5));
		itensRepositorio.saveAll(Arrays.asList(itens,itens2,itens3,itens4,itens5,itens6,itens7));
	}

}
