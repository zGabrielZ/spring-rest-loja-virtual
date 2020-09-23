package com.projeto.spring.lojavirtual.modelo.entidade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoInserirDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Valid
	@NotNull(message = "Usuário não pode ser nulo")
	private UsuarioNovoId usuario;
	
	@NotNull(message = "Número do pedido não pode ser nulo")
	private Long numeroDoPedido;
	
	@Valid
	@NotEmpty(message = "Lista de pedidos não pode ser vazia")
	private List<ItensInserirDTO> itens = new ArrayList<ItensInserirDTO>();
	
}
