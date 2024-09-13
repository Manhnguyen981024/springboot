package com.example.webservice.study_springboot.filtering;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	private MappingJacksonValue jacksonValue;
	private SimpleFilterProvider filterProvider;
	
	@GetMapping("/filter")
	public MappingJacksonValue filter() {
		SomeBean someBean = new SomeBean("field1", "field2", "field3");

		jacksonValue = new MappingJacksonValue(someBean);
		
		filterProvider = new SimpleFilterProvider()
				.addFilter("SomeBeanFilter", SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2"));
		
		jacksonValue.setFilters(filterProvider);
		
		return jacksonValue;
	}
}
