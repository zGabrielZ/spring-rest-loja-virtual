package com.projeto.spring.lojavirtual.repositorio;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.projeto.spring.lojavirtual.modelo.entidade.Pedido;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Long>{

	@Query("select p from Pedido p where p.usuario.id = ?1")
	public List<Pedido> getPedidos(Long idUsuario);
	
	@Query("select p from Pedido p where p.usuario.id = ?1 and p.pedidoStatus ='CANCELADA' ")
	public List<Pedido> getPedidosCancelados(Long idUsuario);
	
	@Query("select p from Pedido p where (coalesce(:min,null) IS NULL OR p.dataDoPedido >= :min) AND (coalesce(:min,null) IS NULL OR p.dataDoPedido <= :max) and p.usuario.id =:idUsuario")
	public List<Pedido> filtrarPeriodo(Date min, Date max,Long idUsuario);
	
	boolean existsByNumeroDoPedido(Long numeroDoPedido);
	
}
