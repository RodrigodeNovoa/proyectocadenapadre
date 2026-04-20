package com.proyectocadena.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectocadena.cliente.entities.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

}
