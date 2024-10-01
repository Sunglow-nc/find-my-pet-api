package com.sunglow.find_my_pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FindMyPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindMyPetApplication.class, args);
	}

}
