package com.projeto.spring.lojavirtual.modelo.entidade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projeto.spring.lojavirtual.modelo.entidade.enums.PedidoStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final String MY_TIME_ZONE="GMT-3";

	private Long id;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm",timezone = MY_TIME_ZONE)
	private Date dataDoPedido;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm",timezone = MY_TIME_ZONE)
	private Date dataDoPedidoFinalizada;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm",timezone = MY_TIME_ZONE)
	private Date dataDoPedidoCancelada;
	
	@Enumerated(EnumType.STRING)
	private PedidoStatus pedidoStatus;
	
	private UsuarioDTO usuario;
	
	private List<ItensDTO> itens = new ArrayList<ItensDTO>();
	
	public Double getTotal() {
		double soma = 0.0;
		for(ItensDTO itens : itens) {
			soma+=itens.getSubTotal();
		}
		return soma;
	}
	
}
