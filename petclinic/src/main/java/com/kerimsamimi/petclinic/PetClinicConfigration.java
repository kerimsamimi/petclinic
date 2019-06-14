package com.kerimsamimi.petclinic;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetClinicConfigration {
	
	@Autowired
	private PetClinicProperties petClinicProperties;
	
	@PostConstruct
	public void init() {
		System.out.println("Display Owners With Pet:" + petClinicProperties.isDisplayOwnerWithPets());
	}
}
