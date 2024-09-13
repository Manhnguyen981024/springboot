package com.example.webservice.study_springboot.jpa;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.example.webservice.study_springboot.user.User;
import com.example.webservice.study_springboot.user.UserNotFoundException;
import com.example.webservice.study_springboot.user.UserResource;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/jpa/users")
	public List<User> queryAllUsers(){
		return this.repository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> queryUser(@PathVariable int id){
		Optional<User> user = this.repository.findById(id);
		
		if (user.isEmpty()) 
			throw new UserNotFoundException("find user with id: " + id + " not found");
		
		EntityModel<User> entityModel = EntityModel.of(user.get());
		
		WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(UserResource.class).queryAllUsers());
		
		entityModel.add(linkBuilder.withRel("all-users"));
		
		return entityModel;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user){
		User savedUser = this.repository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void delete(@PathVariable int id){
		Optional<User> user = this.repository.findById(id);
		this.repository.delete(user.get());
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> getAllPostOfUser (@PathVariable int id){
		Optional<User> user = this.repository.findById(id);
		
		if (user.isEmpty()) 
			throw new UserNotFoundException("find user with id: " + id + " not found");
		
		return user.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> savePostForUser (@PathVariable int id, @Valid @RequestBody Post post){
		Optional<User> user = this.repository.findById(id);
		
		if (user.isEmpty()) 
			throw new UserNotFoundException("find user with id: " + id + " not found");
		
		post.setUser(user.get());
		Post savedPost = this.postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.build()
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
}
