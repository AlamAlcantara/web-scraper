package com.koombea.scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ScraperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScraperApplication.class, args);
	}

}
