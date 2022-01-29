package com.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

}    
