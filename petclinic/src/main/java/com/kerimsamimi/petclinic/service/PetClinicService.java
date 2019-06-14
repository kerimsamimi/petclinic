package com.kerimsamimi.petclinic.service;

import java.util.List;

import com.kerimsamimi.petclinic.exeption.OwnerNotFoundException;
import com.kerimsamimi.petclinic.model.Owner;

public interface PetClinicService {
	List<Owner> findOwners();
	List<Owner> findOwners(String lastName);
	Owner findOwner(Long id) throws OwnerNotFoundException;
	void createOwner(Owner owner);
	void update(Owner owner);
	void deleteOwner(Long id);
}
