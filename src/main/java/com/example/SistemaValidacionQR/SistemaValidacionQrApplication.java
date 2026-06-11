package com.example.SistemaValidacionQR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SistemaValidacionQrApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaValidacionQrApplication.class, args);
	}

}
