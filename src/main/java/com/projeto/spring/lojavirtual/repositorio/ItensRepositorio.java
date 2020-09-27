package com.projeto.spring.lojavirtual.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projeto.spring.lojavirtual.modelo.entidade.Itens;

@Repository
public interface ItensRepositorio extends JpaRepository<Itens, Long>{

	@Query("select i from Itens i inner join i.pedido p where i.id =:idItens and p.id =:idPedido")
	public Optional<Itens> verificarPedidoItens(Long idPedido,Long idItens);
	
	Optional<Itens> findByQuantidade(Integer quantidade);
}
