package com.proyectocadena.producto.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectocadena.producto.entities.Producto;
import com.proyectocadena.producto.repository.ProductoRepositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController // Indicamos el controlador Rest
@RequestMapping("/producto") // Mapeamos la ruta /api como ruta de acceso a la clase
public class ProductoRestController {

	@Autowired // Instancia automaticamente
	ProductoRepositorio productoRepo;

	@GetMapping()
	public List<Producto> findAll() {
		return productoRepo.findAll(); // metodo findAll(), proporcionado por JpaRepository, desde el
											// ConsumidorRepositorio
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable long id) {

		/*
		 * Optional -> objeto contenedor que puede contener o no un valor distinto de
		 * nulo. Si hay un valor, isPresent() devuelve verdadero. Si no hay ningun
		 * valor, devuelve falso.
		 */
		Optional<Producto> producto = productoRepo.findById(id);
		if (producto.isPresent()) {
			return new ResponseEntity<>(producto.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable long id, @RequestBody Producto input) {
		/*
		 * Recibo una entidad Consumidor y su ID que me piden actualizar
		 * Lo busco en la base de datos.
		 * si existe, remapeo esos valores y lo guardo en la base de datos.
		 * Si no, retorno que no existe.
		 */
		Optional<Producto> consumidor = productoRepo.findById(id);
		if (consumidor.isPresent()) {
			Producto nuevoConsumidor = consumidor.get();
			nuevoConsumidor.setCodigo(input.getCodigo());
			nuevoConsumidor.setNombre(input.getNombre());
			Producto guardaConsumidor = productoRepo.save(nuevoConsumidor);
			return new ResponseEntity<>(guardaConsumidor, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody Producto input) {
		Producto save = productoRepo.save(input);
		return ResponseEntity.ok(save);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		productoRepo.deleteById(id);
		return null;
	}

}
