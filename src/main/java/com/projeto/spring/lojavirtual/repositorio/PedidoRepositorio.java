package com.projeto.spring.lojavirtual.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.spring.lojavirtual.modelo.entidade.Pedido;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Long>{

}
