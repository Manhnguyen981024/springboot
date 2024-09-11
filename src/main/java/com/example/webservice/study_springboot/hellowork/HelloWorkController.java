package com.example.webservice.study_springboot.hellowork;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorkController {

	@GetMapping("hello-world")
	public String helloWork() {
		return "Hello world";
	}
	
	@GetMapping("hello-world-bean")
	public HelloWorldBean helloWorkBean() {
		return new HelloWorldBean("Hello world");
	}
	
	@GetMapping("hello-world/path-variable/{name}")
	public HelloWorldBean helloWorkPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello world, %s", name));
	}
}
