package com.kerimsamimi.petclinic.web;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.kerimsamimi.petclinic.model.Owner;

public class PetClinicRestControllerTest {
	private RestTemplate restTemplate;
	
	@Before
	public void setUp() {
		restTemplate=new RestTemplate();
		BasicAuthenticationInterceptor basicAuthenticationInterceptor=
				new BasicAuthenticationInterceptor("user", "secret");
		restTemplate.setInterceptors(Arrays.asList(basicAuthenticationInterceptor));
	}
	
	@Test
	public void testDeleteOwner() {
		restTemplate.delete("http://localhost:9000/rest/owner/2");
		try {
		restTemplate.getForEntity("htttp://localhost:9000/rest/owner/2", Owner.class);
		Assert.fail("Should have not returned Owner");
		}catch(HttpClientErrorException ex){
			MatcherAssert.assertThat(ex.getStatusCode().value(), Matchers.equalTo(404));
		}
	}
	@Test
	public void testCreateOwner() {
		Owner owner=new Owner();
		owner.setFirstName("Yunus");
		owner.setLastName("Samimi");
		
		URI location= restTemplate.postForLocation("http://localhost:9000/rest/owner", owner);
		
		Owner owner2= restTemplate.getForObject(location, Owner.class);
		
		MatcherAssert.assertThat(owner2.getFirstName(), Matchers.equalTo(owner.getFirstName()));
		MatcherAssert.assertThat(owner2.getLastName(), Matchers.equalTo(owner.getLastName()));
	}
	
	
	@Test
	public void testGetOwnerById() {
		ResponseEntity<Owner> response= restTemplate.getForEntity("http://localhost:9095/rest/owner/1", Owner.class);
		
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		//MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Kerim"));
	}
	
	@Test
	public void testGetOwnersByLastName() {
		ResponseEntity<List> response=restTemplate.getForEntity("http://localhost:9000/rest/owner?ln=Samimi", List.class);
		
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<Map<String, String>> body= response.getBody();
		
		List<String> firstNames= body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Kerim"));
	}
	
	@Test
	public void testGetOwners() {
		ResponseEntity<List> response=restTemplate.getForEntity("http://localhost:9000/rest/owners", List.class);
		
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<Map<String, String>> body=response.getBody();
		
		List<String> firstNames= body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Kerim","Yılmaz","Uğurcan","Muhammed"));
	}
}














