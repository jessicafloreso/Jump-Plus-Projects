package com.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.exception.ResourceNotFoundException;
import com.project.model.Furniture;
import com.project.service.FurnitureService;

@RestController
@RequestMapping("/api")
public class FurnitureController {

	@Autowired
	FurnitureService service;
	
	@GetMapping("/furniture")
	public List<Furniture> getFurniture(){
		return service.getFurniture();
	}
	
	@GetMapping("/furniture/id/{id}")
	public ResponseEntity<?> getFurnitureById(@PathVariable int id) throws ResourceNotFoundException{
		Furniture found = service.getFurnitureById(id);
		return ResponseEntity.status(200).body( found );
	}
	
	@GetMapping("/furniture/product/{product}")
	public ResponseEntity<?> getFurnitureProduct(@PathVariable String product) throws ResourceNotFoundException{
		Furniture found = service.getFurnitureByProduct(product);
		return ResponseEntity.status(200).body( found );
	}
	
	@PostMapping("/furniture")
	public ResponseEntity<?> createFurniture(@Valid @RequestBody Furniture furniture){
		furniture.setId(null);
		Furniture created = service.createFurniture(furniture);
		return ResponseEntity.status(201).body(created);
	}
	
	@PutMapping("/furniture")
	public ResponseEntity<?> updateFurniture(@Valid @RequestBody Furniture furniture) throws ResourceNotFoundException{
		Furniture updated = service.updateFurniture(furniture);
		return ResponseEntity.status(200).body(updated);
	}
	
	@DeleteMapping("/furniture/{id}")
	public ResponseEntity<?> deleteFurniture(@PathVariable int id) throws ResourceNotFoundException{
		service.deleteFurniture(id);
		return ResponseEntity.status(200).body("DELETED: item id #" + id);
	}
	
	
	
	
	
}
