package com.damon;

import com.damon.config.AccessLogFilter;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
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

	@Bean
	@ConditionalOnClass(OncePerRequestFilter.class)
	public Filter accessLogFilter() {
		return new AccessLogFilter();
	}
}
