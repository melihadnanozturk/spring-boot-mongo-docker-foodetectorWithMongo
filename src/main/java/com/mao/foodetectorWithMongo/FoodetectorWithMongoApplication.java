package com.mao.foodetectorWithMongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class FoodetectorWithMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodetectorWithMongoApplication.class, args);
	}

}
