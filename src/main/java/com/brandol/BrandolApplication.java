package com.brandol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BrandolApplication {
	public static void main(String[] args) {SpringApplication.run(BrandolApplication.class, args);}
}
