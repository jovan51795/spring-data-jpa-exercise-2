package com.jovscv.springdatajpaexercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringdatajpaExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringdatajpaExerciseApplication.class, args);
	}

}
