package com.proyectocadena.transaccion.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectocadena.transaccion.entities.Transaccion;
import com.proyectocadena.transaccion.repository.TransaccionRepositorio;

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
@RequestMapping("/transaccion") // Mapeamos la ruta /api como ruta de acceso a la clase
public class TransaccionRestController {

	@Autowired // Instancia automaticamente
	TransaccionRepositorio transaccionRepo;
	
	/*
	 * Hago un CRUD:
	 *  -> Buscar todos
	 *  -> Buscar/obtener por Id
	 *  -> Poner/actualizar en la BD
	 *  -> Mostrar
	 *  -> Borrar
	 */

	@GetMapping()
	public List<Transaccion> findAll() {
		return transaccionRepo.findAll(); // metodo findAll(), proporcionado por JpaRepository, desde el
											// ConsumidorRepositorio
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable long id) {

		/*
		 * Optional -> objeto contenedor que puede contener o no un valor distinto de
		 * nulo. Si hay un valor, isPresent() devuelve verdadero. Si no hay ningun
		 * valor, devuelve falso.
		 */
		Optional<Transaccion> producto = transaccionRepo.findById(id);
		if (producto.isPresent()) {
			return new ResponseEntity<>(producto.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable long id, @RequestBody Transaccion input) {
		/*
		 * Recibo un ID(que puede pertenecer a una transaccion) y una entidad Transaccion.
		 * Me piden actualizar la informacion si existe.
		 * Lo busco en la base de datos.
		 * Si existe, re-mapeo todos los valores y lo guardo en la base de datos.
		 * Si no, retorno que no existe.
		 */
		Optional<Transaccion> transaccion = transaccionRepo.findById(id); //puede contener o no una entidad transaccion
		if (transaccion.isPresent()) {
			Transaccion nuevaTransaccion = transaccion.get();
			nuevaTransaccion.setReferencia(input.getReferencia());
			nuevaTransaccion.setCuentaBancaria(input.getCuentaBancaria());
			nuevaTransaccion.setFecha(input.getFecha());
			nuevaTransaccion.setImporte(input.getImporte());
			nuevaTransaccion.setTarifa(input.getTarifa());
			nuevaTransaccion.setDescripcion(input.getDescripcion());
			nuevaTransaccion.setEstatus(input.getEstatus());
			nuevaTransaccion.setCanal(input.getCanal());
			Transaccion guardaTransaccion = transaccionRepo.save(nuevaTransaccion);
			return new ResponseEntity<>(guardaTransaccion, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody Transaccion input) {
		Transaccion save = transaccionRepo.save(input);
		return ResponseEntity.ok(save);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		transaccionRepo.deleteById(id);
		return null;
	}

}
