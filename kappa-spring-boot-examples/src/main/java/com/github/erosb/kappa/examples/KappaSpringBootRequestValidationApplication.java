package com.github.erosb.kappa.examples;

import com.github.erosb.kappa.autoconfigure.EnableKappaRequestValidation;
import com.github.erosb.kappa.autoconfigure.KappaSpringConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.LinkedHashMap;

@SpringBootApplication
@EnableKappaRequestValidation
public class KappaSpringBootRequestValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(KappaSpringBootRequestValidationApplication.class, args);
	}

	@Bean
	public KappaSpringConfiguration kappaConfig() {
		var kappaConfig = new KappaSpringConfiguration();
		var mapping = new LinkedHashMap<String, String>();
		mapping.put("/**", "/openapi/pets-api.yaml");
		kappaConfig.setOpenapiDescriptions(mapping);
		return kappaConfig;
	}
}
