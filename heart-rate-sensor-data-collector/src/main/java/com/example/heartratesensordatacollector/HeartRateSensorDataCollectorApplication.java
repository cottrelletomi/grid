package com.example.heartratesensordatacollector;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class HeartRateSensorDataCollectorApplication {
	@Autowired
	private HeartRateSensorSubscriber heartRateSensorSubscriber;

	public static void main(String[] args) {
		SpringApplication.run(HeartRateSensorDataCollectorApplication.class, args);
	}

	@PostConstruct
	public void run() throws MqttException {
		heartRateSensorSubscriber.subscribe();
	}
}
