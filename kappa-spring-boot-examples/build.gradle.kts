plugins {
	java
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	id("io.github.michael-nestler.spectral") version "0.1.0-0"
}

group = "com.github.erosb"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	implementation("com.github.erosb:kappa-spring:2.0.0")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}


spectral {
	documents.from("src/main/resources/openapi/pets-api.yaml", "src/main/resources/openapi/customer-api.yaml")

	reports {
		junit {
			enabled.set(true)                 // Default: true - Set to false if you really don't want a junit xml report
//			reportFile.set(file("lint.xml"))  // Default: $buildDir/test-results/spectral/spectral.xml
		}
		stylish.set(true)                     // Default: true - Set to false if you don't want console output of the result
	}
}
