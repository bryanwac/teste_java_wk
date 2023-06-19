package com.wk.testejava;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class TesteJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteJavaApplication.class, args);
	}

	@PostConstruct
	void started() {
		// set JVM timezone as UTC
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
