package com.example.orderUpdate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.orderUpdate.service.OrderProcessingService;

@SpringBootApplication
public class OrderUpdateServiceApplication {
	


	public static void main(String[] args) {
		SpringApplication.run(OrderUpdateServiceApplication.class, args);
		
		
	}
	 @Bean
	    CommandLineRunner run(OrderProcessingService service) {
	        return args -> service.processCSV();
	    }
	 
	 
	

}
