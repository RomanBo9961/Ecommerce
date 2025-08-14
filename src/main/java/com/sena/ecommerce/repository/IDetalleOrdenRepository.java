package com.sena.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.ecommerce.model.DetalleOrden;

@Repository
public interface IDetalleOrdenRepository {

	public interface IUsuarioRepository extends JpaRepository<DetalleOrden, Integer>{
		
	}
}
