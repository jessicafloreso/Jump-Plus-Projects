package com.project.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Furniture;


@Repository
public interface FurnitureRepo extends JpaRepository<Furniture, Integer>{
	public Optional<Furniture> findByProduct(String product);
}
