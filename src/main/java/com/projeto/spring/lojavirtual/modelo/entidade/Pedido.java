package com.projeto.spring.lojavirtual.modelo.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.projeto.spring.lojavirtual.modelo.entidade.enums.PedidoStatus;
import com.projeto.spring.lojavirtual.service.exceptions.RegraDeNegocio;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_pedido")
@NoArgsConstructor
@Getter
@Setter
public class Pedido implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date dataDoPedido;
	
	private Date dataDoPedidoFinalizada;
	
	private Date dataDoPedidoCancelada;
	
	@Enumerated(EnumType.STRING)
	private PedidoStatus pedidoStatus;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	 
	@OneToMany(mappedBy = "pedido",fetch = FetchType.EAGER)
	private List<Itens> itens = new ArrayList<Itens>();
	
	public Pedido(Long id, Date dataDoPedido, Date dataDoPedidoCancelada, PedidoStatus pedidoStatus,
			Usuario usuario) {
		this.id = id;
		this.dataDoPedido = dataDoPedido;
		this.dataDoPedidoCancelada = dataDoPedidoCancelada;
		this.pedidoStatus = pedidoStatus;
		this.usuario = usuario;
	}
	
	public Double getTotal() {
		double soma = 0.0;
		for(Itens itens : itens) {
			soma+=itens.getSubTotal();
		}
		return soma;
	}
	
	
	public boolean naoPodeSerCancelada() {
		return PedidoStatus.FINALIZADA.equals(getPedidoStatus()) || PedidoStatus.CANCELADA.equals(getPedidoStatus());
	}
	
	public boolean naoPodeSerFinalizado() {
		return PedidoStatus.FINALIZADA.equals(getPedidoStatus()) || PedidoStatus.CANCELADA.equals(getPedidoStatus());
	}
	
	public void cancelar() {
		
		if(naoPodeSerCancelada()) {
			throw new RegraDeNegocio("Pedido não pode ser cancelado, pois já está cancelada ou finalizada");
		}
		
		setPedidoStatus(PedidoStatus.CANCELADA);
		setDataDoPedidoCancelada(new Date());
	}
	
	public void finalizar() {
		
		if(naoPodeSerFinalizado()) {
			throw new RegraDeNegocio("Pedido não pode ser finalizado, pois já está finalizada ou cancelada");
		}
		
		setPedidoStatus(PedidoStatus.FINALIZADA);
		setDataDoPedidoFinalizada(new Date());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
