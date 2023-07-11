package com.example.everymutsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class EveryMutsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EveryMutsaApplication.class, args);
	}

}
