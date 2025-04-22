package com.ecart.storeservice.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StoreServiceApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(StoreServiceApplicationStarter.class, args);
	}

}
