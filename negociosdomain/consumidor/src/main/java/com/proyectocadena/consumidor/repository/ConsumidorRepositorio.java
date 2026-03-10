package com.proyectocadena.consumidor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectocadena.consumidor.entities.Consumidor;

public interface ConsumidorRepositorio extends JpaRepository<Consumidor, Long> {

}
