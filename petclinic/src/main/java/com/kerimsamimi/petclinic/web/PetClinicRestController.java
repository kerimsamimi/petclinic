package com.kerimsamimi.petclinic.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kerimsamimi.petclinic.exeption.InternalServerExeption;
import com.kerimsamimi.petclinic.exeption.OwnerNotFoundException;
import com.kerimsamimi.petclinic.model.Owner;
import com.kerimsamimi.petclinic.service.PetClinicService;

@RestController
@RequestMapping("/rest")
public class PetClinicRestController {
	
	@Autowired
	private PetClinicService petClinicService;
	
	@RequestMapping(method=RequestMethod.GET, value="/owner/{id}", produces="application/json")
	public ResponseEntity<?> getOwnerAsHateoasResource(@PathVariable("id") Long id){
		try {
			Owner owner=petClinicService.findOwner(id);
			
			Link self= ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner" + id).withSelfRel();
			Link create= ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner").withRel("create");
			Link update= ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner" + id).withRel("update");
			Link delete= ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner" + id).withRel("delete");
			
			Resource<Owner> resource=new Resource<Owner>(owner, self,create,update,delete);
			return ResponseEntity.ok(resource);
			}catch(OwnerNotFoundException ex){
				return ResponseEntity.notFound().build();
			}
	}
	
	
	@RequestMapping(method=RequestMethod.DELETE, value="/owner/{id}")
	public ResponseEntity<?> deleteOwner(@PathVariable("id") Long id){
		try {
			petClinicService.deleteOwner(id);
			return ResponseEntity.ok().build();
		} catch (OwnerNotFoundException ex) {
			throw ex;
		}catch (Exception ex) {
			throw new InternalServerExeption(ex);
		}
		
	}
	
	@RequestMapping(method=RequestMethod.POST , value="/owner")
	public ResponseEntity<URI> createOwner(@RequestBody Owner owner){
		try {
			petClinicService.createOwner(owner);
			Long id=owner.getId();
			URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
			return ResponseEntity.created(location).build();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(method=RequestMethod.GET , value="/owners")
	public ResponseEntity<List<Owner>> getOwners(){
		List<Owner> owners= petClinicService.findOwners();
		return ResponseEntity.ok(owners);
	}
	
	@RequestMapping(method=RequestMethod.GET , value="/owner")
	public ResponseEntity<List<Owner>> getOwners(@RequestParam("ln") String lastName){
		List<Owner> owners= petClinicService.findOwners(lastName);
		return ResponseEntity.ok(owners);
	}	
	
	@RequestMapping(method=RequestMethod.GET , value="/owner/{id}")
	public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id){
		try {
		Owner owner=petClinicService.findOwner(id);
		return ResponseEntity.ok(owner);
		}catch(OwnerNotFoundException ex){
			return ResponseEntity.notFound().build();
		}
	}
	
	
}
