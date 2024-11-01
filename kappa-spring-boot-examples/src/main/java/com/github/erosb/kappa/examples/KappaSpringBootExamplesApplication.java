package com.github.erosb.kappa.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KappaSpringBootExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(KappaSpringBootExamplesApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<OpenApiBasedRequestValidationFilter> filterRegistration() {
		FilterRegistrationBean<OpenApiBasedRequestValidationFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new OpenApiBasedRequestValidationFilter());
		registration.setOrder(2);
		registration.addUrlPatterns("/api/*");
		return registration;
	}

}
