package com.example.awbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

//@ImportResource("classpath:data.sql")
@SpringBootApplication
public class AwbdApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwbdApplication.class, args);
	}

}