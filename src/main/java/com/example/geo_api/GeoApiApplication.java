package com.example.geo_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GeoApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(GeoApiApplication.class, args);
	}

}
