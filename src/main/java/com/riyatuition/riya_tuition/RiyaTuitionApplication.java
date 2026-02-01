package com.riyatuition.riya_tuition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RiyaTuitionApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiyaTuitionApplication.class, args);
	}

}
