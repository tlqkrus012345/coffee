package com.coffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories
public class CoffeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeApplication.class, args);
		/*
		System.out.println(Charset.defaultCharset().displayName());
		run -> edit configuration -> vmoption -> -ea -Dfile.encoding="UTF-8"
		*/
	}
}
