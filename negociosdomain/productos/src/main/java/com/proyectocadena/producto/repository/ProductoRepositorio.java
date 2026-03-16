package com.proyectocadena.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectocadena.producto.entities.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

}
