package com.baartmans.jstratego2021;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class Jstratego2021Application {
	public static void main(String[] args) {
		SpringApplication.run(Jstratego2021Application.class, args);
	}
}