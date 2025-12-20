package com.limito.limitedproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.limito")
public class LimitedProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(LimitedProductApplication.class, args);
	}

}
