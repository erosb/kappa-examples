package com.github.erosb.kappa.examples;

import com.github.erosb.kappa.autoconfigure.EnableKappaRequestValidation;
import com.github.erosb.kappa.autoconfigure.KappaSpringConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.LinkedHashMap;

@SpringBootApplication
@EnableKappaRequestValidation
public class KappaSpringBootExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(KappaSpringBootExamplesApplication.class, args);
	}

	@Bean
	public KappaSpringConfiguration kappaConfig() {
		KappaSpringConfiguration kappaConfig = new KappaSpringConfiguration();
		var mapping = new LinkedHashMap<String, String>();
		mapping.put("/api/pets", "/openapi/pets-api.yaml");
		mapping.put("/api/customers/**", "/openapi/customers-api.yaml");
		kappaConfig.setOpenapiDescriptions(mapping);
		return kappaConfig;
	}
}
