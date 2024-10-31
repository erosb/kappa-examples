package com.github.erosb.openapidemo

import jakarta.servlet.FilterRegistration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean

@SpringBootApplication
class OpenapidemoApplication {

	@Bean
	fun filterReg(): FilterRegistrationBean<OpenApiBackedRequestValidationFilter> {
		val registration = FilterRegistrationBean<OpenApiBackedRequestValidationFilter>()
		registration.filter = OpenApiBackedRequestValidationFilter()
		registration.order = 2
		registration.addUrlPatterns("/api/*")
		return registration
	}
}

fun main(args: Array<String>) {
	runApplication<OpenapidemoApplication>(*args)
}
