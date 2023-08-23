package com.doceasy.worker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doceasy.worker.dto.HealthDTO;

@RequestMapping("/health")
@RestController
public class HealthController {
	
	@GetMapping
	public HealthDTO health() {
		HealthDTO dto = new HealthDTO();
		
		dto.setTimestamp(System.currentTimeMillis());
		dto.setStatus("running");
		
		return dto;
	}
	
}
