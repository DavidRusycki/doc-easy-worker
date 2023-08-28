package com.doceasy.worker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doceasy.worker.dto.HealthDTO;

@RequestMapping("/health")
@RestController
public class HealthController {
	
	private Logger log = LoggerFactory.getLogger(HealthController.class);
	
	@GetMapping
	public HealthDTO health() {
		log.info("running");
		
		HealthDTO dto = new HealthDTO();
		
		dto.setTimestamp(System.currentTimeMillis());
		dto.setStatus("running");
		
		return dto;
	}
	
}
