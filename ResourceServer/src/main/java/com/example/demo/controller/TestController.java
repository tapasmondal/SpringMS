package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	
	//@Value("${name}") String name;

	  

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/hi")
	  public String showLuckyWord() {
	    return "Hi, OAuth2 is working just fine .... ";
	  }
	  
	  
	 
}
