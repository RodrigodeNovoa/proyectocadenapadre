package com.proyectocadena.transaccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectocadena.transaccion.entities.Transaccion;

public interface TransaccionRepositorio extends JpaRepository<Transaccion, Long> {

}
