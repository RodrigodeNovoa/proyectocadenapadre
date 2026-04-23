package com.proyectocadena.cliente.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class ClienteProducto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long idProducto;
	
	@Transient
	private String nombreProducto;
	
	/*
	 * @JsonIgnore -> Marca campos o métodos específicos en una clase Java
	 *  que deben excluirse (ignorar) durante la serialización (objeto a JSON)
	 *  y deserialización (JSON a objeto).
	 *  
	 *  @ManyToOne -> Indica que muchos productos pueden tener un mismo cliente
	 *  
	 *  @JoinColumn -> Especifica la columna de clave foranea (foreign key)
	 *   en la entidad "hija" que se vincula con la clave primaria de otra entidad.
	 */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Cliente.class)
	@JoinColumn
	private Cliente cliente;
}
