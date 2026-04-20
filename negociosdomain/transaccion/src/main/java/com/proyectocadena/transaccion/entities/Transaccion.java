package com.proyectocadena.transaccion.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Transaccion {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;
	private String referencia;
	private String cuentaBancaria;
	private Date fecha; //TODO revisar si se usa el Date de java.util
	private double importe;
	private double tarifa;
	private String descripcion;
	private String estatus; //TODO modificar string por "Status"
	private String canal; //TODO modificar string por "Status"

}
