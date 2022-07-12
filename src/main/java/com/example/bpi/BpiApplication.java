package com.example.bpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * BpiApplication
 * Entry point
 * @version 1.0.0
 * @author Chamil Ananda
 * @Date 7/12/2022
 */
@SpringBootApplication
public class BpiApplication {

	/**
	 * ...Main method...
	 * @param args array of arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(BpiApplication.class, args);
	}
	/**
	 * ...Rest template bean...
	 *
	 */
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
