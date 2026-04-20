package com.proyectocadena.cliente.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cliente {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;
	private String nombre;
	private String apellido;
	private String telefono;
	private String iban;
	private String direccion;

	//TODO continuo aqui
}
