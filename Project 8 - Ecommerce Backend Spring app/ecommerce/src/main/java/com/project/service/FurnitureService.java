package com.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.exception.ResourceNotFoundException;
import com.project.model.Furniture;
import com.project.model.User;
import com.project.repo.FurnitureRepo;

@Service
public class FurnitureService {
	
	@Autowired
	FurnitureRepo repo;
	
	//Create
	public Furniture createFurniture(Furniture furniture){
		furniture.setId(null);
		Furniture created = repo.save(furniture);
		return created;
	}
	
	//Read - get
	public List<Furniture> getFurniture(){
		return repo.findAll();
	}
	
	//Read - get by Id
	public Furniture getFurnitureById(int id) throws ResourceNotFoundException{
		Optional<Furniture> found = repo.findById(id);
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("No such furniture exists");
		}
		return found.get();
	}
	
	//Read - get by product
	public Furniture getFurnitureByProduct(String product) throws ResourceNotFoundException{
		Optional<Furniture> found = repo.findByProduct(product);

		if(found.isEmpty()) {
			throw new ResourceNotFoundException("No such product exists");
		}

		return found.get();
	}
	
	//Update
	public Furniture updateFurniture(Furniture furniture) throws ResourceNotFoundException {
		
		boolean exists = repo.existsById(furniture.getId());
		
		if(exists) {
		
			Furniture updated = repo.save(furniture);
			
			return updated;
			
		}
		
		throw new ResourceNotFoundException( "No such furniture exists" );
	}
	
	//delete
	public boolean deleteFurniture(int id) throws ResourceNotFoundException {
		
		boolean exists = repo.existsById(id);
		
		if(exists) {
			
			repo.deleteById(id);
			
			return true;
			
		}
		
		throw new ResourceNotFoundException("No such furniture exists" );
	}

}
