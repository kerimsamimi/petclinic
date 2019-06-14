package com.kerimsamimi.petclinic.dao;

import java.util.List;

import com.kerimsamimi.petclinic.model.Pet;

public interface PetRepository {
	
	Pet findById(Long id);
	List<Pet> findByOwnerId(Long ownerId);
	void create(Pet pet);
	Pet update(Pet pet);
	void delete(Long id);
	void deleteByOwnerId(Long ownerId);
}
