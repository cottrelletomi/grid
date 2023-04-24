package com.example.heartratesensorworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class HeartRateSensorWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeartRateSensorWorkerApplication.class, args);
	}

}
