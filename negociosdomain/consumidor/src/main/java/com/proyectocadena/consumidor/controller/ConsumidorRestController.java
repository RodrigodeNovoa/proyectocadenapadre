package com.proyectocadena.consumidor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectocadena.consumidor.entities.Consumidor;
import com.proyectocadena.consumidor.repository.ConsumidorRepositorio;

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
@RequestMapping("/consumidor") // Mapeamos la ruta /api como ruta de acceso a la clase
public class ConsumidorRestController {

	@Autowired // Instancia automaticamente
	ConsumidorRepositorio consumidorRepo;

	@GetMapping()
	public List<Consumidor> findAll() {
		return consumidorRepo.findAll(); // metodo findAll(), proporcionado por JpaRepository, desde el
											// ConsumidorRepositorio
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable long id) {

		/*
		 * Optional -> objeto contenedor que puede contener o no un valor distinto de
		 * nulo. Si hay un valor, isPresent() devuelve verdadero. Si no hay ningun
		 * valor, devuelve falso.
		 */
		Optional<Consumidor> consumidor = consumidorRepo.findById(id);
		if (consumidor.isPresent()) {
			return new ResponseEntity<>(consumidor.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable long id, @RequestBody Consumidor input) {
		/*
		 * Recibo una entidad Consumidor y su ID que me piden actualizar
		 * Lo busco en la base de datos.
		 * si existe, remapeo esos valores y lo guardo en la base de datos.
		 * Si no, retorno que no existe.
		 */
		Optional<Consumidor> consumidor = consumidorRepo.findById(id);
		if (consumidor.isPresent()) {
			Consumidor nuevoConsumidor = consumidor.get();
			nuevoConsumidor.setName(input.getName());
			nuevoConsumidor.setPhone(input.getPhone());
			Consumidor guardaConsumidor = consumidorRepo.save(nuevoConsumidor);
			return new ResponseEntity<>(guardaConsumidor, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody Consumidor input) {
		Consumidor save = consumidorRepo.save(input);
		return ResponseEntity.ok(save);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		consumidorRepo.deleteById(id);
		return null;
	}

}
