package com.sunglow.find_my_pet;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FindMyPetApplication {
	static{
		Dotenv dotenv = Dotenv.configure().load();
		System.setProperty("NEON_DB_URL", dotenv.get("NEON_DB_URL"));
		System.setProperty("NEON_DB_USERNAME", dotenv.get("NEON_DB_USERNAME"));
		System.setProperty("NEON_DB_PASSWORD", dotenv.get("NEON_DB_PASSWORD"));
	}
	public static void main(String[] args) {
		SpringApplication.run(FindMyPetApplication.class, args);
	}

}
