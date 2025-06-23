package com.example.pract6_ServWeb;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "model")
@ComponentScan(basePackages = {"controller", "model"})
@EntityScan(basePackages = "model")

public class Pract6ServWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(Pract6ServWebApplication.class, args);
	}

}
