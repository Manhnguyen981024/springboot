package com.example.webservice.study_springboot.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {

	@Autowired
	private UserDAOService daoService;
	
	@GetMapping("/users")
	public List<User> queryAllUsers(){
		return this.daoService.findAll();
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> queryUser(@PathVariable int id){
		User user = this.daoService.findOne(id);
		if (user == null) 
			throw new UserNotFoundException("find user with id: " + id + " not found");
		
		EntityModel<User> entityModel = EntityModel.of(user);
		
		WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(UserResource.class).queryAllUsers());
		
		entityModel.add(linkBuilder.withRel("all-users"));
		
		return entityModel;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user){
		User savedUser = this.daoService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void delete(@PathVariable int id){
		User user = this.daoService.findOne(id);
		this.daoService.delete(user);
	}
}
