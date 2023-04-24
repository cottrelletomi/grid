package com.example.notificationchannelmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EntityScan("org.example.core.models")
public class NotificationChannelManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationChannelManagerApplication.class, args);
	}

}
