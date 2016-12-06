package com.fiveonechain.digitasset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ChcDigitassetApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChcDigitassetApplication.class, args);
	}
}
