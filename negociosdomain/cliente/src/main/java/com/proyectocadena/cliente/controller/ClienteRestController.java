package com.proyectocadena.cliente.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectocadena.cliente.entities.Cliente;
import com.proyectocadena.cliente.repository.ClienteRepositorio;

import jakarta.transaction.Transactional;

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
@RequestMapping("/cliente") // Mapeamos la ruta /api como ruta de acceso a la clase
public class ClienteRestController {

	@Autowired // Instancia automaticamente
	ClienteRepositorio clienteRepo;

	/*
	 * Metodo findAll(), proporcionado por JpaRepository, 
	 * desde el ConsumidorRepositorio
	 */
	@GetMapping()
	public List<Cliente> findAll() {
		return clienteRepo.findAll();
	}

	/*
	 * Busca por id de cliente y devuelve el cliente con id coincidente si exite, sino error (NOsuchElement...)
	 */
	@GetMapping("/{id}")
	public Cliente get(@PathVariable long id) {
		
		return clienteRepo.findById(id).get();

	}

	
	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable long id, @RequestBody Cliente input) {
		/*
		 * Recibo una entidad Cliente y su ID, me piden actualizar el cliente
		 * Lo busco en la base de datos, y lo obtengo
		 * si existe, remapeo esos valores y lo guardo en la base de datos.
		 */
		Cliente cliente = clienteRepo.findById(id).get();
		if (cliente != null) {
			
			cliente.setCodigo(input.getCodigo());
			cliente.setNombre(input.getNombre());
			cliente.setApellido(input.getApellido());
			cliente.setTelefono(input.getTelefono());
			cliente.setIban(input.getIban());
			cliente.setDireccion(input.getDireccion());
		}
		Cliente clienteActualizado = clienteRepo.save(cliente);
			return ResponseEntity.ok(clienteActualizado);
		
	}

	/* Recibe un objeto Customer (Cliente) con sus productos relacionados, 
	 * establece la relación bidireccional correctamente, 
	 * guarda todo en la base de datos y devuelve el cliente guardado.
	 * 
	 * Este método maneja peticiones HTTP POST.
	 * ResponseEntity<?>: Retorna una respuesta HTTP completa (código de estado, headers, cuerpo). 
	 * El ? significa que el tipo del cuerpo puede ser cualquier cosa (generico).
	 * 
	 * @RequestBody Customer input: Spring convierte automáticamente 
	 * el JSON del cuerpo de la petición en un objeto Customer
	 * 
	 */
	@PostMapping
	public ResponseEntity<?> post(@RequestBody Cliente input) {
		//input.getProducts() → Obtiene la lista de productos del cliente.
		//.forEach(x -> ...) → Itera sobre cada producto.
		//x.setCustomer(input) → Establece la relación inversa.
		input.getProductos().forEach(x -> x.setCliente(input));
		/*
		 * Al recibir el JSON, el cliente tiene la lista de productos,
		 * pero cada producto NO tiene referencia al cliente. 
		 * Sin esta lInea, cuando JPA intente guardar, los productos 
		 * tendrían customer_id = NULL en la base de datos, causando error.
		 */
		
		Cliente clienteActualizado = clienteRepo.save(input);
		return ResponseEntity.ok(clienteActualizado); //Respuesta HTTP con codigo 200, OK
	}

	/*
	 * Eliminar un cliente de la base de datos usando su ID.
	 * Clientes que no existen → 404 Not Found
	 * Clientes con productos relacionados → Limpieza de relaciones antes de eliminar
	 * Operación exitosa → 204 No Content
	 * 
	 * 
	 */
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable long id) {
		
		//Consulta la base de datos buscando un cliente con ese ID. Uso de Optional por que puedo o no existir.
		
		Optional<Cliente> clientePorID = clienteRepo.findById(id);
		
		//Si existre =true, sino existe =false
		if(clientePorID.isPresent()) {
			//Me aseguro de que existe un cliente por ID antes de extraer el objeto cliente del optional (NoSuchElementException )		
			Cliente clienteExiste = clientePorID.get(); 
		
			//limpia relaciones antes de eliminar (opcional):
			//verifica que tenga productos asociados.
			if(clienteExiste.getProductos() != null) {
				// Itera sobre cada producto y rompe la relación bidireccional.
				//Si la BD tiene una restricción de clave foranea no permite eliminar si hay productos referenciando
				clienteExiste.getProductos().forEach(producto -> producto.setCliente(null));
				//Vacia la coleccion de productos del cliente
				clienteExiste.getProductos().clear();
			}
			//ejecuta el delete en la BD (DELETE FROM customer WHERE id = ?)
			clienteRepo.delete(clienteExiste);
			
			return ResponseEntity.noContent().build(); // 204 no content (Delete exitoso)
		}else {
			return ResponseEntity.notFound().build(); // 404 not found
			}
		
	}
	
	/* EJEMPLO DE CODIGO JSON:
	 * 
	 * {
  		"nombre": "Juan Pérez",
  		"email": "juan@email.com",
  		"products": [
			{"nombre": "Laptop", "precio": 1000.0},
			{"nombre": "Mouse", "precio": 25.0}
		]
		}
	 * 
	 */
}
