package com.example.webservice.study_springboot.hellowork;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorkController {
	@Autowired
	private MessageSource messageSource;
	

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
	
	@GetMapping("hello-world-international")
	public String helloWorkInternational() {
		Locale locale = LocaleContextHolder.getLocale();
		
		System.out.println(locale);
		return messageSource.getMessage("good.morning.message", null, "default message", locale);
	}
}
