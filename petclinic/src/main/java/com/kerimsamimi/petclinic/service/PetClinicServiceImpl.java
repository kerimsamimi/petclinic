package com.kerimsamimi.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kerimsamimi.petclinic.dao.OwnerRepository;
import com.kerimsamimi.petclinic.dao.PetRepository;
import com.kerimsamimi.petclinic.exeption.OwnerNotFoundException;
import com.kerimsamimi.petclinic.model.Owner;

@Service
@Transactional
public class PetClinicServiceImpl implements PetClinicService {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private PetRepository petRepository;
	
	
	/*public void setOwnerReposotory(OwnerRepository ownerReposotory) {
		this.ownerRepository = ownerRepository;
	}*/

	@Override
	public List<Owner> findOwners() {
		return ownerRepository.findAll();
	}

	@Override
	public List<Owner> findOwners(String lastName) {
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	public Owner findOwner(Long id) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(id);
		if(owner==null) throw new OwnerNotFoundException("Owner Not Found With Id:" + id);
		return owner;
	}

	@Override
	public void createOwner(Owner owner) {
		ownerRepository.create(owner);
	}

	@Override
	public void update(Owner owner) {
		ownerRepository.update(owner);
	}

	@Override
	public void deleteOwner(Long id) {
		petRepository.deleteByOwnerId(id);
		ownerRepository.delete(id);
		//if(true) throw new RuntimeException("texting rollback");
	}

	

}
