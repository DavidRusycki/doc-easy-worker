package com.doceasy.worker;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class DocEasyWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocEasyWorkerApplication.class, args);
	}

}
