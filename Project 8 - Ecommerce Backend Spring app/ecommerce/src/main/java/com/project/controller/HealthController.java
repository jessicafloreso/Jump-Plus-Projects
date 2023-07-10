package com.project.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
	
	@GetMapping("/api/hello")
	public String getHealth() {
		return "Hello from Panther";
	}
	
	@GetMapping("/api/admin")
	public String getHello() {
		return "Hello Admin";
	}
}