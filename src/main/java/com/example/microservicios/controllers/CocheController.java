package com.example.microservicios.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.starter.CocheService;
import com.starter.models.Coche;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;


//Controlador del coche para borrar, añadir y actualizar la lista de los coches.

@RestController
public class CocheController {
	
	private final static Logger logger = LoggerFactory.getLogger(CocheController.class);
	
	@Autowired
	CocheService servicio;
	
	private Counter counterGetAll;
	private Counter counterGetById;
	private Counter counterUpdate;
	private Counter counterPost;
	private Counter counterDelete;
	

	public CocheController(MeterRegistry registry) {
		this.counterGetAll = Counter.builder("peticiones.getall").register(registry);
		this.counterGetById = Counter.builder("peticiones.getbyid").register(registry);
		this.counterUpdate = Counter.builder("peticiones.update").register(registry);
		this.counterPost = Counter.builder("peticiones.post").register(registry);
		this.counterDelete = Counter.builder("peticiones.delete").register(registry);
	}
	
	//Recogida de la lista de coches
	
	@GetMapping()
	public ResponseEntity<List<Coche>> getAll(){
		counterGetAll.increment();
		return ResponseEntity.status(HttpStatus.OK).body(servicio.getAll());
	}
	
	//Busqueda de coche
	
	@GetMapping("/{id}")
	public ResponseEntity<Coche> getById(@PathVariable("id") Long id){
		counterGetById.increment();
		Optional<Coche> resul = servicio.getById(id);
		if(resul.isEmpty()) {
			logger.info("Coche no encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(resul.get());
		}
	}
	
	
	//Actualizar coche en la lista
	
	@PutMapping()
	public ResponseEntity<Void> updateCoche(@RequestBody Coche cocheUpdated){
		counterUpdate.increment();
		servicio.updateCoche(cocheUpdated);
		logger.info("Coche actualizado");
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	
	//Crear nuevo coche en la lista
	
	@PostMapping()
	public ResponseEntity<Void> nCoche(@RequestBody Coche nCoche){
		counterPost.increment();
		servicio.addCoche(nCoche);
		logger.info("Coche creado.");
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	
	//Borrar coche de la lista
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCoche(@PathVariable("id") Long id){
		counterDelete.increment();
		servicio.deleteCocheId(id);
		logger.info("Coche borrado");
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
