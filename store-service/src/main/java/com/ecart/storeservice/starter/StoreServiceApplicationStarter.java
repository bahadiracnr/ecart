package com.ecart.storeservice.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages ={"com.ecart"})
@EntityScan(basePackages ={"com.ecart"})
@EnableJpaRepositories(basePackages={"com.ecart"})
@SpringBootApplication
@EnableCaching
public class StoreServiceApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(StoreServiceApplicationStarter.class, args);
	}

}
