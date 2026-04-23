package com.proyectocadena.cliente.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class Cliente {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;
	private String codigo;
	private String nombre;
	private String apellido;
	
	private String telefono;
	private String iban;
	private String direccion;

	/*
	 * Un cliente puede tener uno o muchos productos, 
	 * que se guardan en una lista de tipo:(ClienteProductos, 
	 * que es una asociaciuon entre el id del cliente y el id de los productos).
	 * 
	 * @OneToMany -> define una relación de uno a muchos entre dos entidades. 
	 * Permite que una entidad "padre" tenga una colección de entidades "hijas" asociadas.
	 * Generalmente, se usa junto con @ManyToOne en la clase hija para lograr una relación bidireccional
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ClienteProducto> productos; 
	
	/*
	 *  Guarda todas las transacciones que un ciente puede hacer con un producto.
	 *  @Transient -> indica que un campo de una entidad no debe ser persistido en la base de datos
	 */
	@Transient
	private List<?> transacciones;
	
}
