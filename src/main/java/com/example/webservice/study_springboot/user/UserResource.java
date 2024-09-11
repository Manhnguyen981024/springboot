package com.example.webservice.study_springboot.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {

	@Autowired
	private UserDAOService daoService;
	
	@GetMapping("/users")
	public List<User> queryAllUsers(){
		return this.daoService.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User queryUser(@PathVariable int id){
		return this.daoService.findOne(id);
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> saveUser(@RequestBody User user){
		User savedUser = this.daoService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}
