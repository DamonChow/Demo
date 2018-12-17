package com.damon;

import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Validator validator() {
		ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
				.configure()
				.failFast(Boolean.TRUE)
				.buildValidatorFactory();
		return factory.getValidator();
	}
}
