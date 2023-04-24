package com.example.emergencynotificationagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("org.example.core.models")
public class EmergencyNotificationAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmergencyNotificationAgentApplication.class, args);
	}

}
